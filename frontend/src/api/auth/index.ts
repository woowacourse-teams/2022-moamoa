import type { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import axiosInstance from '@api/axiosInstance';

export type ApiLogin = {
  post: {
    params: { code: string };
    variables: ApiLogin['post']['params'];
  };
};

export type ApiLoginStatus = {
  get: {
    responseData: {
      isLoggedIn: boolean;
    };
  };
};

// login
export const postLogin = async ({ code }: ApiLogin['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiLogin['post']['variables']>(
    `/api/auth/login?code=${code}`,
  );
  return response.data;
};

export const usePostLogin = () => useMutation<null, AxiosError, ApiLogin['post']['variables']>(postLogin);

// logout
export const deleteLogout = async () => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, null>(`/api/auth/logout`);
  return response.data;
};

export const useDeleteLogout = () => useMutation<null, AxiosError, null>(deleteLogout);

// refresh
export const getRefresh = async () => {
  const response = await axiosInstance.get<null, AxiosResponse<null>, null>(`/api/auth/refresh`);
  return response.data;
};

// initial login status
export const getLoginStatus = async () => {
  const response = await axiosInstance.get<ApiLoginStatus['get']['responseData']>(`/api/auth/login/status`);
  return response.data;
};
