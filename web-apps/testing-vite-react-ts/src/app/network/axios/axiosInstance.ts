import axios from 'axios';
import {getConfigForEnvironment} from "../../../config.ts";

const mode = import.meta.env.MODE;
const config = getConfigForEnvironment(mode);

const axiosInstance = axios.create({
    baseURL: config.BACKEND_URL,
    timeout: 3000,
    headers: {
        'Content-Type': 'application/json',
    },
});

export default axiosInstance;