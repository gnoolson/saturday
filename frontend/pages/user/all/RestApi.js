import axios from "@/rest_api/api"

function defaultErrorHandler(error) {
    console.error(error);
}

function getAll(successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/user/all").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error)
    });
}

function setPassword(dto, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/user/set-password", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function del(dto, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/user/delete", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function create(dto, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/user/create", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function setRole(dto, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/user/set-role", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    getAll,
    setPassword,
    del,
    create,
    setRole
}