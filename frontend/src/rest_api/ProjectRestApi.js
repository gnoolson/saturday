import axios from "@/rest_api/api"

function defaultErrorHandler(error) {
    console.error(error);
}

function get(projectId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/project/" + projectId).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function getAll(successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/project/all").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error)
    });
}

function save(project, successCallback, errorCallback) {
    axios.post("/api/project/upsert", project).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function getAvailable(successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/root/projects").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error)
    });
}

function del(projectId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/project/" + projectId + "/delete").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    getAll,
    getAvailable,
    get,
    save,
    del
};