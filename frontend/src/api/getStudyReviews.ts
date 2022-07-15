import { StudyReview } from '@custom-types/index';

import axiosInstance from './axiosInstance';

const getStudyReviews = async (studyId: string): Promise<{ reviews: Array<StudyReview> }> => {
  const response = await axiosInstance.get<{ reviews: Array<StudyReview> }>(`/api/studies/${studyId}/review`);
  return response.data;
};

export default getStudyReviews;
