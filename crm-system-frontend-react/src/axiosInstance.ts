import axios from 'axios';

// import { store } from '@/redux/store';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/',  // Adjust base URL as per your backend
});

axiosInstance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token"); // Assuming you retrieve token from Redux state
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default axiosInstance;
