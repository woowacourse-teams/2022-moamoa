import type { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { MyStudy } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const QK_MY_STUDIES = 'my-studies';

// get
export type GetMyStudiesResponseData = {
  studies: Array<MyStudy>;
};

export const getMyStudies = async () => {
  const response = await axiosInstance.get<GetMyStudiesResponseData>(`/api/my/studies`);
  return response.data;
};

export const useGetMyStudies = () => {
  return useQuery<GetMyStudiesResponseData, AxiosError>(QK_MY_STUDIES, getMyStudies);
};
