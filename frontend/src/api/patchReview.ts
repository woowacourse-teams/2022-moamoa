import { EditReviewQueryData, EmptyObject } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const patchReview = async ({ studyId, reviewId, content }: EditReviewQueryData) => {
  const response = await axiosInstance.patch<EmptyObject, any, EditReviewQueryData>(
    `/api/studies/${studyId}/reviews/${reviewId}`,
    {
      reviewId,
      studyId,
      content,
    },
  );
  return response.data;
};
