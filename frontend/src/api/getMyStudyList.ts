import type { GetMyStudyResponseData } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const getMyStudyList = async () => {
  const response = await axiosInstance.get<GetMyStudyResponseData>(`/api/my/studies`);
  return response.data;
};
