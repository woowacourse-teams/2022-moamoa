import type { GetMyStudyResponseData } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

const getMyStudyList = async () => {
  const response = await axiosInstance.get<GetMyStudyResponseData>(`/api/my/studies`);
  return response.data;
};

export default getMyStudyList;
