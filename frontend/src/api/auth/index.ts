import { AxiosError, type AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import checkType, { isNull } from '@utils/typeChecker';

import { checkLogin, checkRefresh } from '@api/auth/typeChecker';
import axiosInstance, { refreshAxiosInstance } from '@api/axiosInstance';

export type ApiLogin = {
  post: {
    params: { code: string };
    responseData: {
      accessToken: string;
      expiredTime: number;
    };
    variables: ApiLogin['post']['params'];
  };
};

export type ApiRefreshToken = {
  get: {
    responseData: {
      accessToken: string;
      expiredTime: number;
    };
  };
};

// login
export const postLogin = async ({ code }: ApiLogin['post']['variables']) => {
  const response = await axiosInstance.post<
    ApiLogin['post']['responseData'],
    AxiosResponse<ApiLogin['post']['responseData']>,
    ApiLogin['post']['variables']
  >(`/api/auth/login?code=${code}`);

  return checkLogin(response.data);
};

export const usePostLogin = () =>
  useMutation<ApiLogin['post']['responseData'], AxiosError, ApiLogin['post']['variables']>(postLogin);

// refresh - get new access token
export const getRefreshAccessToken = async () => {
  const response = await refreshAxiosInstance.get<ApiRefreshToken['get']['responseData']>(`/api/auth/refresh`);
  return checkRefresh(response.data);
};
