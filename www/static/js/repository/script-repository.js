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

export default {
    getAll,
    enable,
    isEnabled,
    getNumberOfErrors,
    updateCode,
    get,
    upsert
};