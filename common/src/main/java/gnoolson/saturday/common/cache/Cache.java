package gnoolson.saturday.common.cache;

import gnoolson.locker.Locker;
import gnoolson.saturday.common.locker.LockId;
import gnoolson.saturday.common.model.vo.Id;
import gnoolson.saturday.common.time.TimeProvider;
import gnoolson.saturday.common.time.vo.TimeInSec;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Cache<I extends Id, V> {

    private final Map<I, CachedEntity> cache = new ConcurrentHashMap<>();
    private final TimeProvider timeProvider;
    private final long cleaningInterval;
    private final Timer timer = new Timer();
    private final EntityProvider<I, V> entityProvider;
    private final Locker locker;

    /*
     *
     *
     * */
    public Cache(TimeInSec cleaningInterval, TimeProvider timeProvider, EntityProvider<I, V> entityProvider, Locker locker) {
        if (cleaningInterval.getValue() < 1)
            throw new IllegalArgumentException("Cleaning interval cannot be less than 1 second"); // +

        this.cleaningInterval = cleaningInterval.getValue() * 1000L;
        this.timeProvider = timeProvider;
        this.entityProvider = entityProvider;
        this.locker = locker;

        startSelfCleaning();
    }

    public Cache(TimeInSec cleaningInterval, TimeProvider timeProvider, Locker locker) {
        this(cleaningInterval, timeProvider, null, locker);
    }

    public V getAndProlongOrCreate(I id) {
        if (entityProvider == null)
            throw new RuntimeException("EntityProvider was not found"); // +

        return getAndProlongOrCreate(id, entityProvider);
    }

    public V getAndProlongOrCreate(I id, EntityProvider<I, V> entityProvider) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            CachedEntity cachedEntity = cache.get(id);
            long now = timeProvider.now().getValue();

            if (cachedEntity == null) {
                Entity<V> entity = entityProvider.create(id);
                if (!entity.getCachingTime().isCacheable()) {
                    return entity.getValue();
                }
                cachedEntity = new CachedEntity(entity, now);
                cache.put(id, cachedEntity);
            }

            return cachedEntity.getAndProlong(now);
        }
    }

    public Set<I> getIds() {
        return cache.keySet();
    }

    public Optional<V> getAndProlongIfExists(I id) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            CachedEntity cachedData = cache.get(id);
            if (cachedData == null)
                return Optional.empty();

            long now = timeProvider.now().getValue();
            if (cachedData.isExpired(now))
                return Optional.empty();

            return Optional.of(cachedData.getAndProlong(now));
        }
    }

    public Optional<V> getIfExists(I id) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            CachedEntity cachedData = cache.get(id);
            if (cachedData == null)
                return Optional.empty();

            if (cachedData.isExpired(timeProvider.now().getValue()))
                return Optional.empty();

            return Optional.of(cachedData.get());
        }
    }

    public void removeOld() {
        long now = timeProvider.now().getValue();
        for (Map.Entry<I, CachedEntity> entry : cache.entrySet()) {
            if (entry.getValue().isExpired(now))
                remove(entry.getKey());
        }
    }

    public void remove(I id) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            CachedEntity cachedEntity = cache.remove(id);
            if (cachedEntity != null)
                cachedEntity.destroy();
        }
    }

    public boolean exists(I id) {
        try (Locker.LockHandle ignore = locker.lockIds(LockId.of(id))) {
            return cache.containsKey(id);
        }
    }

    public void removeAll() {
        cache.clear();
    }

    /*
     *
     *
     * */
    private void startSelfCleaning() {
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                removeOld();
            }
        }, 0, cleaningInterval);
    }


    /*
     *
     *
     * */
    private class CachedEntity {
        private final Entity<V> entity;
        private final long storageTime;
        private long expired;

        CachedEntity(Entity<V> entity, long now) {
            this.entity = entity;
            this.storageTime = entity.getCachingTime().getValue() * 1000L;
            this.expired = now + storageTime;
        }

        V getAndProlong(long nowSec) {
            this.expired = nowSec + this.storageTime;
            return this.entity.getValue();
        }

        V get() {
            return this.entity.getValue();
        }

        boolean isExpired(long now) {
            return now > expired;
        }

        public void destroy() {
            entity.destroy();
        }
    }

}
