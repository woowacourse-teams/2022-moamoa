import type { AxiosError } from 'axios';
import { QueryKey, UseQueryOptions, useQuery } from 'react-query';

import type { Member, StudyId, UserRole } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

// study role
export type GetUserRoleRequestParams = {
  studyId: StudyId;
};
export type GetUserRoleResponseData = {
  role: UserRole;
};
type UseGetUserRole = GetUserRoleRequestParams & {
  options?: Omit<
    UseQueryOptions<GetUserRoleResponseData, AxiosError, GetUserRoleResponseData, QueryKey>,
    'queryKey' | 'queryFn'
  >;
};

export const getUserRole = async ({ studyId }: GetUserRoleRequestParams) => {
  const response = await axiosInstance.get<GetUserRoleResponseData>(`/api/members/me/role?study-id=${studyId}`);
  return response.data;
};

export const useGetUserRole = ({ studyId, options }: UseGetUserRole) =>
  useQuery<GetUserRoleResponseData, AxiosError>(['my-role', studyId], () => getUserRole({ studyId }), options);

// user info
export type GetUserInformationResponseData = Member;

export const getUserInformation = async () => {
  const response = await axiosInstance.get<GetUserInformationResponseData>(`/api/members/me`);
  return response.data;
};

export const useGetUserInformation = () =>
  useQuery<GetUserInformationResponseData, AxiosError>('user-info', getUserInformation, {
    enabled: false,
  });
