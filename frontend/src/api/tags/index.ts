import type { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { Tag } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

// get
export type GetTagsResponseData = {
  tags: Array<Tag>;
};

export const getTags = async () => {
  const response = await axiosInstance.get<GetTagsResponseData>(`/api/tags`);
  return response.data;
};

export const useGetTags = () => {
  return useQuery<GetTagsResponseData, AxiosError>('filters', getTags);
};
