import type { AxiosError } from 'axios';
import { QueryKey, UseQueryOptions, useQuery } from 'react-query';

import type { Member, StudyId, UserRole } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkMember, checkUserRole } from '@api/member/typeChecker';

export type ApiUserRole = {
  get: {
    params: {
      studyId: StudyId;
    };
    responseData: {
      role: UserRole;
    };
    variables: ApiUserRole['get']['params'] & {
      options?: Omit<
        UseQueryOptions<ApiUserRole['get']['responseData'], AxiosError, ApiUserRole['get']['responseData'], QueryKey>,
        'queryKey' | 'queryFn'
      >;
    };
  };
};

export type ApiUserInformation = {
  get: {
    responseData: Member;
  };
};

export const getUserRole = async ({ studyId }: ApiUserRole['get']['variables']) => {
  const response = await axiosInstance.get<ApiUserRole['get']['responseData']>(
    `/api/members/me/role?study-id=${studyId}`,
  );
  return checkUserRole(response.data);
};

export const useGetUserRole = ({ studyId, options }: ApiUserRole['get']['variables']) =>
  useQuery<ApiUserRole['get']['responseData'], AxiosError>(
    ['my-role', studyId],
    () => getUserRole({ studyId }),
    options,
  );

export const getUserInformation = async () => {
  const response = await axiosInstance.get<ApiUserInformation['get']['responseData']>(`/api/members/me`);
  return checkMember(response.data);
};

export const useGetUserInformation = () =>
  useQuery<ApiUserInformation['get']['responseData'], AxiosError>('user-info', getUserInformation, {
    enabled: false,
  });
