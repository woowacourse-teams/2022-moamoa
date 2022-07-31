import { DeleteReviewQueryData, EmptyObject } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const deleteReview = async ({ studyId, reviewId }: DeleteReviewQueryData) => {
  const response = await axiosInstance.delete<EmptyObject, any, DeleteReviewQueryData>(
    `/studyroom/${studyId}/reviews/${reviewId}`,
  );
  return response.data;
};
