import axios from 'axios';

const api = axios.create({
    withCredentials: true
});

api.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response && error.response.status === 401) {
            window.location.href = "/login";
        }

        return Promise.reject(error);
    }
);

export default api;