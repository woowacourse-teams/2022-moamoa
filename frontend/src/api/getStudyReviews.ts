import { StudyReview } from '@custom-types/index';

import axiosInstance from './axiosInstance';

export type StudyReviewResponse = {
  reviews: Array<StudyReview>;
  totalResults: number;
};

const getStudyReviews = async (studyId: string, size: number, loadAll: boolean): Promise<StudyReviewResponse> => {
  const url = loadAll ? `/api/studies/${studyId}/reviews` : `/api/studies/${studyId}/reviews?size=${size}`;
  const response = await axiosInstance.get<StudyReviewResponse>(url);
  return response.data;
};

export default getStudyReviews;
