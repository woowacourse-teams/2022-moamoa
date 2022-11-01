import { type AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { Tag } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkTags } from '@api/tags/typeChecker';

export type ApiTags = {
  get: {
    responseData: {
      tags: Array<Tag>;
    };
  };
};

const getTags = async () => {
  const response = await axiosInstance.get<ApiTags['get']['responseData']>(`/api/tags`);
  return checkTags(response.data);
};

export const useGetTags = () => {
  return useQuery<ApiTags['get']['responseData'], AxiosError>('filters', getTags);
};
