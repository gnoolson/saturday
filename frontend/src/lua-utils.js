import axios from "axios";

function beautify(code, successCallback, errorCallback) {
    axios.post("/api/script/beautify", code, { headers: { 'Content-Type': 'text/plain' } }).then((response) => {
       successCallback(response.data);
    }, (error) => {
        errorCallback(error);
    });
}

export default {
    beautify
}