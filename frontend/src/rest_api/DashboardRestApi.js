import axios from "@/rest_api/api"

function defaultErrorHandler(error) {
    console.error(error);
}

function getAvailable(successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/root/dashboards").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error)
    });
}

function getAll(successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/dashboard/all"
    ).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function get(dashboardId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/dashboard/" + dashboardId).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function upsert(dashboard, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/dashboard/upsert", dashboard).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function updateCode(dto, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;
    axios.post("/api/dashboard/update-code", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function del(dashboardId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/dashboard/" + dashboardId + "/delete").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    getAvailable,
    getAll,
    get,
    upsert,
    updateCode,
    del
};