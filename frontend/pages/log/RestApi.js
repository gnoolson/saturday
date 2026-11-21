import axios from "@/rest_api/api"

function defaultErrorHandler(error) {
    console.error(error);
}

function load(rows, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/log?rows=" + rows).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    load
}