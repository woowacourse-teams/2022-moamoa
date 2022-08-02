import type { GetTagListResponseData } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

const getTagList = async () => {
  const response = await axiosInstance.get<GetTagListResponseData>(`/api/tags`);
  return response.data;
};

export default getTagList;
