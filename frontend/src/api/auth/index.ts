import type { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import axiosInstance from '@api/axiosInstance';

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
  return response.data;
};

export const usePostLogin = () =>
  useMutation<ApiLogin['post']['responseData'], AxiosError, ApiLogin['post']['variables']>(postLogin);

// logout
export const deleteLogout = async () => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, null>(`/api/auth/logout`);
  return response.data;
};

export const useDeleteLogout = () => useMutation<null, AxiosError, null>(deleteLogout);

export const getRefresh = async () => {
  const response = await axiosInstance.get<ApiRefreshToken['get']['responseData']>(`/api/auth/refresh`);
  return response.data;
};
