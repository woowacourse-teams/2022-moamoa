import { type AxiosError, type AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import { checkType, isNull } from '@utils';

import type { ReviewId, StudyId, StudyReview } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export type ApiReview = {
  post: {
    params: {
      studyId: StudyId;
    };
    body: Pick<StudyReview, 'content'>;
    variables: ApiReview['post']['params'] & ApiReview['post']['body'];
  };
  put: {
    params: {
      studyId: StudyId;
      reviewId: ReviewId;
    };
    body: ApiReview['post']['body'];
    variables: ApiReview['put']['params'] & ApiReview['put']['body'];
  };
  delete: {
    params: {
      studyId: StudyId;
      reviewId: ReviewId;
    };
    variables: ApiReview['delete']['params'];
  };
};

const postReview = async ({ studyId, content }: ApiReview['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiReview['post']['body']>(
    `/api/studies/${studyId}/reviews`,
    {
      content,
    },
  );
  return checkType(response.data, isNull);
};
export const usePostReview = () => useMutation<null, AxiosError, ApiReview['post']['variables']>(postReview);

const putReview = async ({ studyId, reviewId, content }: ApiReview['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiReview['put']['body']>(
    `/api/studies/${studyId}/reviews/${reviewId}`,
    {
      content,
    },
  );
  return checkType(response.data, isNull);
};
export const usePutReview = () => useMutation<null, AxiosError, ApiReview['put']['variables']>(putReview);

const deleteReview = async ({ studyId, reviewId }: ApiReview['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, ApiReview['delete']['variables']>(
    `/api/studies/${studyId}/reviews/${reviewId}`,
  );
  return checkType(response.data, isNull);
};
export const useDeleteReview = () => useMutation<null, AxiosError, ApiReview['delete']['variables']>(deleteReview);
