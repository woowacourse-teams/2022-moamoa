import { AxiosResponse } from 'axios';

import { EmptyObject, PostReviewRequestBody, PostReviewRequestVariables } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const postReview = async ({ studyId, content }: PostReviewRequestVariables) => {
  const response = await axiosInstance.post<EmptyObject, AxiosResponse<EmptyObject>, PostReviewRequestBody>(
    `/api/studies/${studyId}/reviews`,
    {
      content,
    },
  );
  return response.data;
};
