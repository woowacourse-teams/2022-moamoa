import { type AxiosError } from 'axios';
import { type QueryKey, UseQueryOptions, useQuery } from 'react-query';

import type { Member, StudyId, UserRole } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkUserInformation, checkUserRole } from '@api/member/typeChecker';

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
    variables: {
      options?: Omit<
        UseQueryOptions<
          ApiUserInformation['get']['responseData'],
          AxiosError,
          ApiUserInformation['get']['responseData'],
          QueryKey
        >,
        'queryKey' | 'queryFn'
      >;
    };
  };
};

const getUserRole = async ({ studyId }: ApiUserRole['get']['variables']) => {
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

const getUserInformation = async () => {
  const response = await axiosInstance.get<ApiUserInformation['get']['responseData']>(`/api/members/me`);
  return checkUserInformation(response.data);
};

export const useGetUserInformation = ({ options }: ApiUserInformation['get']['variables']) =>
  useQuery<ApiUserInformation['get']['responseData'], AxiosError>('user-info', getUserInformation, options);
