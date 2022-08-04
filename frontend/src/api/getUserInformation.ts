import type { GetUserInformation } from '@custom-types';

import { axiosInstance } from '@api';

const getUserInformation = async () => {
  const response = await axiosInstance.get<GetUserInformation>(`/api/members/me`);
  return response.data;
};

export default getUserInformation;
