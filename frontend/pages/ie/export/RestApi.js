import axios from "@/rest_api/api"

function exportAction(dto, successCallback, errorCallback) {
    axios.post("/api/ie/export", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    exportAction
}