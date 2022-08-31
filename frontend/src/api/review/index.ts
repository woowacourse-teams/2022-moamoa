import type { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import type { ReviewId, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export type ApiReview = {
  post: {
    params: {
      studyId: StudyId;
    };
    body: {
      content: string;
    };
    variables: ApiReview['post']['params'] & ApiReview['post']['body'];
  };
  put: {
    params: {
      studyId: number;
      reviewId: number;
    };
    body: {
      content: string;
    };
    variables: ApiReview['put']['params'] & ApiReview['put']['body'];
  };
  delete: {
    body: {
      studyId: StudyId;
      reviewId: ReviewId;
    };
    variables: ApiReview['delete']['body'];
  };
};

export const postReview = async ({ studyId, content }: ApiReview['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiReview['post']['body']>(
    `/api/studies/${studyId}/reviews`,
    {
      content,
    },
  );
  return response.data;
};

export const usePostReview = () => useMutation<null, AxiosError, ApiReview['post']['variables']>(postReview);

export const putReview = async ({ studyId, reviewId, content }: ApiReview['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiReview['put']['body']>(
    `/api/studies/${studyId}/reviews/${reviewId}`,
    {
      content,
    },
  );
  return response.data;
};

export const usePutReview = () => useMutation<null, AxiosError, ApiReview['put']['variables']>(putReview);

export const deleteReview = async ({ studyId, reviewId }: ApiReview['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, ApiReview['delete']['variables']>(
    `/api/studies/${studyId}/reviews/${reviewId}`,
  );
  return response.data;
};

export const useDeleteReview = () => useMutation<null, AxiosError, ApiReview['delete']['body']>(deleteReview);
