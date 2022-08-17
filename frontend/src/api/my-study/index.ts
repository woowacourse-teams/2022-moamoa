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
