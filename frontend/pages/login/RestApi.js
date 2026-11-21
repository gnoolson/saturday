import axios from "@/rest_api/api"

function auth(requestBody, successCallback, errorCallback) {
	axios.post("/api/login", requestBody).then((response) => {
		successCallback(response.data);
	}, (error) => {
		errorCallback(error);
	});
} 

export default {
    auth
}