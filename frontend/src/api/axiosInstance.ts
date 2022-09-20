import axios, { type AxiosError } from 'axios';

import { API_ERROR } from '@constants';

import LoginStatusController from '@auth/loginStatus';

const axiosInstance = axios.create({
  baseURL: process.env.API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

const handleAxiosError = async (error: AxiosError<{ message: string; code?: number }>) => {
  const response = error.response;
  const config = error.config;

  if (response?.status === 401 && response?.data.code !== API_ERROR.EXPIRED_REFRESH_TOKEN.CODE) {
    // accessToken 인증 실패 + refreshToken은 만료되지 않음
    return LoginStatusController.fetchAccessTokenWithRefresh(() => axiosInstance.request(config));
  }

  const data = error.response?.data;
  if (data?.message) {
    console.error(data.message);
    // TODO: 커스텀 에러 코드를 만들어서 그에 맞는 message를 담은 error 객체를 return 하도록 해야 함
    return Promise.reject(error);
  }

  console.error('서버에 에러가 발생했습니다.', `코드: ${error.code}`);
  error.message = '알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요 :(';
  return Promise.reject(error);
};

axiosInstance.interceptors.response.use(response => response, handleAxiosError);

export default axiosInstance;
