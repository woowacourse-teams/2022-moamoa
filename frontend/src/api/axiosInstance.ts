import axios from 'axios';
import type { AxiosError } from 'axios';

import { getRefreshAccessToken } from '@api/auth';

import AccessTokenController from '@auth/accessTokenController';

const axiosConfig = {
  baseURL: process.env.API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
};

const axiosInstance = axios.create(axiosConfig);
const refreshAxiosInstance = axios.create(axiosConfig);

const handleAxiosError = (error: AxiosError<{ message: string; code?: number }>) => {
  if (error.response?.status === 401) {
    AccessTokenController.clear();
    alert('장시간 접속하지 않아 로그아웃되었습니다.');
    window.location.reload();
    return Promise.reject(error);
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
refreshAxiosInstance.interceptors.response.use(response => response, handleAxiosError);

axiosInstance.interceptors.request.use(
  async config => {
    if (!AccessTokenController.hasAccessToken || !AccessTokenController.hasTokenDateTime) return config;

    const tokenDateTime = AccessTokenController.tokenDateTime;
    if (!tokenDateTime) return config;

    const { properDateTime, expiredDateTime } = tokenDateTime;
    const today = new Date();
    const willTokenBeExpired = properDateTime < today && today < expiredDateTime;
    if (willTokenBeExpired) {
      // refresh access token
      const { accessToken: newAccessToken, expiredTime: newExpiredTime } = await getRefreshAccessToken();
      AccessTokenController.setAccessToken(newAccessToken);
      AccessTokenController.setTokenDateTime(newExpiredTime);
    }

    const accessToken = AccessTokenController.accessToken;
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

refreshAxiosInstance.interceptors.request.use(
  config => {
    if (!AccessTokenController.hasAccessToken || !AccessTokenController.hasTokenDateTime) return config;

    const accessToken = AccessTokenController.accessToken;
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

export { axiosInstance as default, refreshAxiosInstance };
