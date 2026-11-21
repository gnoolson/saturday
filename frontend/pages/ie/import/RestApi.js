import axios from "@/rest_api/api"

function upload(formData, successCallback, errorCallback) {
    axios.post("/api/ie/upload", formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    }).then((response) => {
        successCallback(response.data)
    }, (error) => {
        errorCallback(error);
    });
}

function importAction(dto, successCallback, errorCallback) {
    axios.post("/api/ie/import", dto).then((response) => {
        successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    upload,
    importAction
}