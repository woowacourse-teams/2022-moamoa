import type { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { StudyReview } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const QK_STUDY_REVIEWS = 'study-reviews';

export type ApiReviews = {
  get: {
    params: {
      studyId: number;
      size?: number;
    };
    responseData: {
      reviews: Array<StudyReview>;
      totalCount: number;
    };
    variables: ApiReviews['get']['params'];
  };
};

export const getStudyReviews = async ({ studyId, size = 8 }: ApiReviews['get']['variables']) => {
  const url = size ? `/api/studies/${studyId}/reviews?size=${size}` : `/api/studies/${studyId}/reviews`;
  const response = await axiosInstance.get<ApiReviews['get']['responseData']>(url);
  return response.data;
};

export const useGetStudyReviews = ({ studyId, size }: ApiReviews['get']['variables']) => {
  const queryKey = size ? [QK_STUDY_REVIEWS, studyId] : [QK_STUDY_REVIEWS, studyId, 'all'];
  return useQuery<ApiReviews['get']['responseData'], AxiosError>(queryKey, () => getStudyReviews({ studyId, size }));
};
