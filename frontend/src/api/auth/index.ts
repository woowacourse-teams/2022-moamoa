import type { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import axiosInstance from '@api/axiosInstance';

// login
export type PostLoginRequestParams = {
  code: string;
};
export type PostLoginResponseData = {
  accessToken: string;
  expiredTime: number;
};

export const postLogin = async ({ code }: PostLoginRequestParams) => {
  const response = await axiosInstance.post<
    PostLoginResponseData,
    AxiosResponse<PostLoginResponseData>,
    PostLoginRequestParams
  >(`/api/auth/login?code=${code}`);
  return response.data;
};

export const usePostLogin = () => useMutation<PostLoginResponseData, AxiosError, PostLoginRequestParams>(postLogin);

// logout
export const deleteLogout = async () => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, null>(`/api/auth/logout`);
  return response.data;
};

export const useDeleteLogout = () => useMutation<null, AxiosError, null>(deleteLogout);

// refresh
export type GetRefreshResponseData = {
  accessToken: string;
  expiredTime: number;
};

export const getRefresh = async () => {
  const response = await axiosInstance.get<GetRefreshResponseData>(`/api/auth/refresh`);
  return response.data;
};
