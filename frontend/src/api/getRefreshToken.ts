import type { GetTokenResponseData } from '@custom-types';

import { axiosInstance } from '@api';

const getRefreshToken = async () => {
  const response = await axiosInstance.get<GetTokenResponseData>(`/api/auth/refresh`);
  return response.data;
};

export default getRefreshToken;
