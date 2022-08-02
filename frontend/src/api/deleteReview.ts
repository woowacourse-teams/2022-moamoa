import type { AxiosResponse } from 'axios';

import type { DeleteReviewRequestBody, EmptyObject } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const deleteReview = async ({ studyId, reviewId }: DeleteReviewRequestBody) => {
  const response = await axiosInstance.delete<EmptyObject, AxiosResponse<EmptyObject>, DeleteReviewRequestBody>(
    `/api/studies/${studyId}/reviews/${reviewId}`,
  );
  return response.data;
};
