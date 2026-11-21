import axios from "@/rest_api/api"

function defaultErrorHandler(error) {
    console.error(error);
}

function getAll(successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/plugin/all").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function control(pluginId, flag, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/plugin/" + pluginId + "/control?flag=" + flag).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    getAll,
    control
}