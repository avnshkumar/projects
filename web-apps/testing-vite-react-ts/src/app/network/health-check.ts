import { sendGetRequest } from "./axios/httpClient.js";

const BASE_URL = "/health-check";

export const backendHealthCheck = async () => {
    return await sendGetRequest(BASE_URL);
};

export const mongodbHealthCheck = async () => {
    return await sendGetRequest(`${BASE_URL}/mongodb`);
}

export const postgresHealthCheck = async () => {
    return await sendGetRequest(`${BASE_URL}/postgres`);
}