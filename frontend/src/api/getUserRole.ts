import type { GetUserRoleRequestParams, GetUserRoleResponseData } from '@custom-types';

import { axiosInstance } from '@api';

const getUserRole = async ({ studyId }: GetUserRoleRequestParams) => {
  const response = await axiosInstance.get<GetUserRoleResponseData>(`/api/members/me/role?study-id=${studyId}`);
  return response.data;
};

export default getUserRole;
