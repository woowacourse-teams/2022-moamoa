import { EmptyObject, ReviewQueryData } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const postReview = async ({ studyId, content }: ReviewQueryData) => {
  const url = `/api/studies/${studyId}/reviews`;
  const response = await axiosInstance.post<EmptyObject, any, { content: string }>(url, {
    content,
  });
  return response.data;
};
