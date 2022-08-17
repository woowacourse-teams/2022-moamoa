import type { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import type { StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

// post - join study
export type PostMyStudyRequestParams = {
  studyId: StudyId;
};

export const postMyStudy = async ({ studyId }: PostMyStudyRequestParams) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, PostMyStudyRequestParams>(
    `/api/studies/${studyId}/members`,
  );
  return response.data;
};

export const usePostMyStudy = () => useMutation<null, AxiosError, PostMyStudyRequestParams>(postMyStudy);

// delete - quit study
export type DeleteMyStudyRequestParams = {
  studyId: StudyId;
};

export const deleteMyStudy = async ({ studyId }: DeleteMyStudyRequestParams) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, DeleteMyStudyRequestParams>(
    `/api/studies/${studyId}/members`,
  );
  return response.data;
};

export const useDeleteMyStudy = () => useMutation<null, AxiosError, DeleteMyStudyRequestParams>(deleteMyStudy);
