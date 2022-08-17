import type { GetMyStudiesResponseData } from '@custom-types';

import { axiosInstance } from '@api';

const getMyStudies = async () => {
  const response = await axiosInstance.get<GetMyStudiesResponseData>(`/api/my/studies`);
  return response.data;
};

export default getMyStudies;
