import axios from "@/rest_api/api"

function createEditor(dto, successCallback, errorCallback) {
    axios.post("/api/create-super", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    createEditor
}