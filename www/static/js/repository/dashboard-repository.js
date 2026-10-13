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


export default {
    getAvailable,
};