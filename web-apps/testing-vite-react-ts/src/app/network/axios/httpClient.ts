// Helper to add query params
import {AxiosRequestConfig, AxiosResponse} from "axios";
import axiosInstance from "./axiosInstance.ts";


type requestBody = string | FormData | Record<string, unknown>;


const buildUrlWithParams = (url: string, params?: URLSearchParams): string => {
    const query = new URLSearchParams(params).toString();
    return query ? `${url}?${query}` : url;
};



/*
    How to handle multipart files
    Configure headers
    Explore more options with varying capabilities
*/

export const sendGetRequest = async <T>(
    url: string,
    params?: URLSearchParams,
    config?: AxiosRequestConfig
): Promise<AxiosResponse<T>> => {
    const finalUrl = buildUrlWithParams(url, params);
    return axiosInstance.get<T>(finalUrl, config);
};

export const sendPostRequest = async <T>(
    url: string,
    data?: requestBody,
    config?: AxiosRequestConfig
): Promise<AxiosResponse<T>> => {
    return axiosInstance.post<T>(url, data, config);
};

export const sendPutRequest = async <T>(
    url: string,
    data?: requestBody,
    config?: AxiosRequestConfig
): Promise<AxiosResponse<T>> => {
    return axiosInstance.put<T>(url, data, config);
};

export const sendPatchRequest = async <T>(
    url: string,
    data?: requestBody,
    config?: AxiosRequestConfig
): Promise<AxiosResponse<T>> => {
    return axiosInstance.patch<T>(url, data, config);
};

export const sendDeleteRequest = async <T>(
    url: string,
    config?: AxiosRequestConfig
): Promise<AxiosResponse<T>> => {
    return axiosInstance.delete<T>(url, config);
};