### Крок 3

Створіть власний клас плагіну, який розширює клас `gnoolson.saturday_plugin_api.SaturdayPlugin`.

`PluginManager` керує життєвим циклом плагіну.

Методи життєвого циклу:

- `void start()` - викликається під час запуску плагіну;
- `void stop()` - викликається під час зупинки плагіну;
- `void onApplicationReady()` - викликається після повного запуску **Saturday**, коли система готова до роботи (викликається після `start()`).

```java
public class MyCachePlugin extends SaturdayPlugin {

    @Getter
    public MyCache cache;

    @Override
    public void start() {
        this.cache = new MyCacheImpl(); // будь-яка реалізація MyCache
    }

    @Override
    public void stop() {
        this.cache.clear();
    }

    @Override
    public void onApplicationReady() {
    }

}
```

---