import axios from "@/rest_api/api"

function defaultErrorHandler(error) {
    console.error(error);
}

function getAll(successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/script/all"
    ).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function enable(scriptId, flag, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/script/" + scriptId + "/enable?flag=" + flag).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function isEnabled(scriptId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/script/" + scriptId + "/is_enabled").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function getNumberOfErrors(scriptId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/script/" + scriptId + "/errors/counter").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function updateCode(dto, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/script/update-code", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function get(scriptId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/script/" + scriptId).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function upsert(script, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/script/upsert", script).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function del(scriptId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/script/" + scriptId + "/delete").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function getErrors(scriptId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/script/" + scriptId + "/errors"
    ).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function clearErrors(scriptId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/script/" + scriptId + "/errors/clear"
    ).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function getLog(scriptId, rows, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/script/" + scriptId + "/log?rows=" + rows).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function clearLog(scriptId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/script/" + scriptId + "/log/clear").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function isDebugLogLevelAvailable(scriptId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/script/" + scriptId + "/log/debug-level").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function setDebugLogLevel(scriptId, flag, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/script/" + scriptId + "/log/debug-level?flag=" + flag).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    getAll,
    enable,
    isEnabled,
    getNumberOfErrors,
    updateCode,
    get,
    upsert,
    del,
    getErrors,
    clearErrors,
    getLog,
    clearLog,
    isDebugLogLevelAvailable,
    setDebugLogLevel
};