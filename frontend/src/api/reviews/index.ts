import type { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { StudyReview } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export const QK_STUDY_REVIEWS = 'study-reviews';

// get
export type GetReviewsRequestParams = {
  studyId: number;
  size?: number;
};
export type GetReviewsResponseData = {
  reviews: Array<StudyReview>;
  totalCount: number;
};

export const getStudyReviews = async ({ studyId, size }: GetReviewsRequestParams) => {
  const url = size ? `/api/studies/${studyId}/reviews?size=${size}` : `/api/studies/${studyId}/reviews`;
  const response = await axiosInstance.get<GetReviewsResponseData>(url);
  return response.data;
};

export const useGetStudyReviews = (studyId: number, size?: number) => {
  const queryKey = size ? [QK_STUDY_REVIEWS, studyId] : [QK_STUDY_REVIEWS, studyId, 'all'];
  return useQuery<GetReviewsResponseData, AxiosError>(queryKey, () => getStudyReviews({ studyId, size }));
};
