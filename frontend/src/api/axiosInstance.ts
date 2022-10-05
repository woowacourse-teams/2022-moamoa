import axios from 'axios';
import type { AxiosError } from 'axios';

import AccessTokenController from '@auth/accessToken';

const axiosInstance = axios.create({
  baseURL: process.env.API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

const handleAxiosError = (error: AxiosError<{ message: string; code?: number }>) => {
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

axiosInstance.interceptors.response.use(response => {
  // 서버에서 아무 응답 데이터도 오지 않으면 빈 스트링 ''이 오므로 명시적으로 null로 지정
  if (response.data !== '') return response;
  response.data = null;
  return response;
}, handleAxiosError);

axiosInstance.interceptors.request.use(
  config => {
    const accessToken = AccessTokenController.accessToken;

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
