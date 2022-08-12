import type { AxiosResponse } from 'axios';

import { axiosInstance } from '@api';

const deleteRefreshToken = async () => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, null>(`/api/auth/logout`);
  return response.data;
};

export default deleteRefreshToken;
