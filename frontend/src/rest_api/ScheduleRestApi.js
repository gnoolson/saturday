import axios from "@/rest_api/api"

function defaultErrorHandler(error) {
    console.error(error);
}

function getAll(successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/schedule/all").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function control(scheduleId, flag, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/schedule/" + scheduleId + "/enable?flag=" + flag
    ).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function get(scheduleId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.get("/api/schedule/" + scheduleId).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function save(schedule, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/schedule/upsert", schedule).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function del(scheduleId, successCallback, errorCallback) {
    if (!errorCallback)
        errorCallback = defaultErrorHandler;

    axios.post("/api/schedule/" + scheduleId + "/delete").then((response) => {
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
    del
}