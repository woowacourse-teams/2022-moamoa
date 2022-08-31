import type { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { Tag } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export type ApiTags = {
  get: {
    responseData: {
      tags: Array<Tag>;
    };
  };
};

export const getTags = async () => {
  const response = await axiosInstance.get<ApiTags['get']['responseData']>(`/api/tags`);
  return response.data;
};

export const useGetTags = () => {
  return useQuery<ApiTags['get']['responseData'], AxiosError>('filters', getTags);
};
