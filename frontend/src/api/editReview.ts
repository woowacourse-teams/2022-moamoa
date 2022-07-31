import { EditReviewQueryData, EmptyObject } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const editReview = async ({ studyId, reviewId, content }: EditReviewQueryData) => {
  const response = await axiosInstance.post<EmptyObject, any, { content: string }>(
    `/studyroom/${studyId}/reviews/${reviewId}`,
    {
      content,
    },
  );
  return response.data;
};
