import { PATH } from '@constants';

import { EmptyObject, ReviewQueryData } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const postReview = async ({ studyId, content }: ReviewQueryData) => {
  const response = await axiosInstance.post<EmptyObject, any, { content: string }>(PATH.REVIEW(studyId), {
    content,
  });
  return response.data;
};
