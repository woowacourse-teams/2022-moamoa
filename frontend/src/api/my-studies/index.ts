import { type AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { MyStudy } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const QK_MY_STUDIES = 'my-studies';

export type ApiMyStudies = {
  get: {
    responseData: {
      studies: Array<MyStudy>;
    };
  };
};

export const getMyStudies = async () => {
  const response = await axiosInstance.get<ApiMyStudies['get']['responseData']>(`/api/my/studies`);
  return response.data;
};

export const useGetMyStudies = () => {
  return useQuery<ApiMyStudies['get']['responseData'], AxiosError>(QK_MY_STUDIES, getMyStudies);
};
