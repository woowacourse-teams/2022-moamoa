import type { AxiosResponse } from 'axios';

import type { EmptyObject } from '@custom-types';

import { axiosInstance } from '@api';

const deleteRefreshToken = async () => {
  const response = await axiosInstance.delete<EmptyObject, AxiosResponse<EmptyObject>, EmptyObject>(`/api/auth/logout`);
  return response.data;
};

export default deleteRefreshToken;
