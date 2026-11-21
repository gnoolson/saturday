import axios from "@/rest_api/api"

function getBroker(successCallback) {
    axios.get("/api/mqtt-broker").then((response) => {
        successCallback(response.data);
    }, (error) => {
        console.error(this.errors);
    });
}

function stopBroker(successCallback, errorCallback) {
    axios.post("/api/mqtt-broker/shutdown").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error)
    });
}

function startBroker(dto, successCallback, errorCallback) {
    axios.post("/api/mqtt-broker/start", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function getConnectedClients(successCallback, errorCallback) {
    axios.get("/api/mqtt-broker/connected-client/all").then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function getUsers(successCallback) {
    axios.get("/api/mqtt-broker/user/all").then((response) => {
        successCallback(response.data);
    }, (error) => {
        console.error(error);
    });
}

function deleteUser(dto, successCallback, errorCallback) {
    axios.post("/api/mqtt-broker/user/delete", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

function createNewUser(dto, successCallback, errorCallback) {
    axios.post("/api/mqtt-broker/user/create", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    getBroker, 
    stopBroker,
    startBroker,
    getConnectedClients,
    createNewUser,
    deleteUser,
    getUsers
}