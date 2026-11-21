import axios from "@/rest_api/api"

function defaultErrorHandler(error) {
    console.error(error);
}

function getAll(successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/client/all").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function control(clientId, flag, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/client/" + clientId + "/enable?flag=" + flag).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function get(clientId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/client/" + clientId).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function save(client, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/client/upsert", client).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function del(clientId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/client/" + clientId + "/delete").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function getSubscriptions(clientId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/client/" + clientId + "/subscription/all"
    ).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function getSubscription(clientId, subscriptionId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    const url = "/api/client/" + clientId + "/subscription/" + subscriptionId;
    axios.get(url).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function saveSubscription(clientId, subscription, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/client/" + clientId + "/subscription/upsert", subscription).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function delSubscription(clientId, subscriptionId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/client/" + clientId + "/subscription/" + subscriptionId + "/delete").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    getAll,
    control,
    get,
    save,
    del,
    getSubscriptions,
    getSubscription,
    saveSubscription,
    delSubscription
};