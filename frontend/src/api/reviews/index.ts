import { type AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { Size, StudyId, StudyReview } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkStudyReviews } from '@api/reviews/typeChecker';

export const QK_STUDY_REVIEWS = 'study-reviews';

export type ApiReviews = {
  get: {
    params: {
      studyId: StudyId;
      size?: Size;
    };
    responseData: {
      reviews: Array<StudyReview>;
      totalCount: number;
    };
    variables: ApiReviews['get']['params'];
  };
};

export const getStudyReviews = async ({ studyId, size }: ApiReviews['get']['variables']) => {
  const url = size ? `/api/studies/${studyId}/reviews?size=${size}` : `/api/studies/${studyId}/reviews`;
  const response = await axiosInstance.get<ApiReviews['get']['responseData']>(url);
  return checkStudyReviews(response.data);
};

export const useGetStudyReviews = ({ studyId, size }: ApiReviews['get']['variables']) => {
  const queryKey = size ? [QK_STUDY_REVIEWS, studyId] : [QK_STUDY_REVIEWS, studyId, 'all'];
  return useQuery<ApiReviews['get']['responseData'], AxiosError>(queryKey, () => getStudyReviews({ studyId, size }));
};
