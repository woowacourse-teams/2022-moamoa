import type { GetUserInformationResponseData } from '@custom-types';

import { axiosInstance } from '@api';

const getUserInformation = async () => {
  const response = await axiosInstance.get<GetUserInformationResponseData>(`/api/members/me`);
  return response.data;
};

export default getUserInformation;
