import type { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import { checkType, isNull } from '@utils';

import type { StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export type ApiMyStudy = {
  post: {
    params: {
      studyId: StudyId;
    };
    variables: ApiMyStudy['post']['params'];
  };
  delete: {
    params: { studyId: StudyId };
    variables: ApiMyStudy['delete']['params'];
  };
};

// TODO: postMyStudy -> postSignUpMyStudy
export const postMyStudy = async ({ studyId }: ApiMyStudy['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiMyStudy['post']['variables']>(
    `/api/studies/${studyId}/members`,
  );
  return checkType(response.data, isNull);
};

export const usePostMyStudy = () => useMutation<null, AxiosError, ApiMyStudy['post']['variables']>(postMyStudy);

export const deleteMyStudy = async ({ studyId }: ApiMyStudy['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, ApiMyStudy['delete']['variables']>(
    `/api/studies/${studyId}/members`,
  );
  return checkType(response.data, isNull);
};

export const useDeleteMyStudy = () => useMutation<null, AxiosError, ApiMyStudy['delete']['variables']>(deleteMyStudy);
