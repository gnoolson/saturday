const TIMEOUTS = []; // [{ name, callback, delay, timeoutId }]
let mainIntervalId = null;

function run(interval) {
    stop();

    mainIntervalId = setInterval(() => {
        for (const timeout of TIMEOUTS) {
            if (timeout.timeoutId !== null)
                continue;

            timeout.timeoutId = setTimeout(() => {
                timeout.timeoutId = null;
                const index = TIMEOUTS.indexOf(timeout);
                if (index !== -1)
                    TIMEOUTS.splice(index, 1);

                try {
                    timeout.callback();
                } catch (error) {
                    console.error(error);
                }

            }, timeout.delay);
        }

    }, interval);
}

function stop() {
    if (mainIntervalId !== null) {
        clearInterval(mainIntervalId);
        mainIntervalId = null;
    }
}

function cancel(name) {
    for (let i = TIMEOUTS.length - 1; i >= 0; i--) {
        const timeout = TIMEOUTS[i];
        if (timeout.name !== name)
            continue;

        if (timeout.timeoutId !== null)
            clearTimeout(timeout.timeoutId);

        TIMEOUTS.splice(i, 1);
    }
}

function exec(name, callback, delay) {
    cancel(name);
    TIMEOUTS.push({
        name,
        callback,
        delay,
        timeoutId: null
    });
}

run(100);

export default {
    exec,
    cancel,
    stop,
    run
};