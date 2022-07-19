import { StudyReview } from '@custom-types/index';

import axiosInstance from '@api/axiosInstance';

export type StudyReviewResponse = {
  reviews: Array<StudyReview>;
  totalResults: number;
};

const getStudyReviews = async (studyId: number, size?: number): Promise<StudyReviewResponse> => {
  const url = size ? `/api/studies/${studyId}/reviews` : `/api/studies/${studyId}/reviews?size=${size}`;
  const response = await axiosInstance.get<StudyReviewResponse>(url);
  return response.data;
};

export default getStudyReviews;
