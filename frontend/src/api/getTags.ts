import type { GetTagsResponseData } from '@custom-types';

import { axiosInstance } from '@api';

const getTags = async () => {
  const response = await axiosInstance.get<GetTagsResponseData>(`/api/tags`);
  return response.data;
};

export default getTags;
