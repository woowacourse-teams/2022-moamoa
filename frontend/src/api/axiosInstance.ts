import axios, { AxiosError, AxiosResponse } from 'axios';

const axiosInstance = axios.create({
  baseURL: process.env.API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

const handleAxiosError = (error: AxiosError) => {
  const { data } = error.response as AxiosResponse<{ message: string }>;
  if (data?.message) {
    throw new Error(data.message);
  }

  throw new Error('뭔가 axios에 에러가 발생했습니다');
};

axiosInstance.interceptors.response.use(response => response, handleAxiosError);

axiosInstance.interceptors.request.use(
  config => {
    const accessToken = window.sessionStorage.getItem('accessToken');
    if (!accessToken) return config;
    if (!config.headers) {
      config.headers = {
        Authorization: `Bearer ${accessToken}`,
      };
      return config;
    }
    config.headers['Authorization'] = `Bearer ${accessToken}`;
    return config;
  },
  error => {
    return Promise.reject(error);
  },
);

export default axiosInstance;
