import type { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import type { ReviewId, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

// post
export type PostReviewRequestParams = {
  studyId: StudyId;
};
export type PostReviewRequestBody = {
  content: string;
};
export type PostReviewRequestVariables = PostReviewRequestParams & PostReviewRequestBody;

export const postReview = async ({ studyId, content }: PostReviewRequestVariables) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, PostReviewRequestBody>(
    `/api/studies/${studyId}/reviews`,
    {
      content,
    },
  );
  return response.data;
};

export const usePostReview = () => useMutation<null, AxiosError, PostReviewRequestVariables>(postReview);

// patch
export type PutReviewRequestParams = {
  studyId: number;
  reviewId: number;
};
export type PutReviewRequestBody = {
  content: string;
};
export type PutReviewRequestVariables = PutReviewRequestParams & PutReviewRequestBody;

export const putReview = async ({ studyId, reviewId, content }: PutReviewRequestVariables) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, PutReviewRequestBody>(
    `/api/studies/${studyId}/reviews/${reviewId}`,
    {
      content,
    },
  );
  return response.data;
};

export const usePutReview = () => useMutation<null, AxiosError, PutReviewRequestVariables>(putReview);

// delete
export type DeleteReviewRequestBody = {
  studyId: StudyId;
  reviewId: ReviewId;
};

export const deleteReview = async ({ studyId, reviewId }: DeleteReviewRequestBody) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, DeleteReviewRequestBody>(
    `/api/studies/${studyId}/reviews/${reviewId}`,
  );
  return response.data;
};

export const useDeleteReview = () => useMutation<null, AxiosError, DeleteReviewRequestBody>(deleteReview);
