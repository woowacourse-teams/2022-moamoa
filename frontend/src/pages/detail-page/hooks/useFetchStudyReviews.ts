import { QK_FETCH_STUDY_REVIEWS } from '@constants';
import { useQuery } from 'react-query';

import getStudyReviews from '@api/getStudyReviews';
import type { StudyReviewResponse } from '@api/getStudyReviews';

const useFetchStudyReviews = (studyId: number, size?: number) => {
  const queryKey = size ? [QK_FETCH_STUDY_REVIEWS, studyId] : [QK_FETCH_STUDY_REVIEWS, studyId, 'all'];
  return useQuery<StudyReviewResponse, unknown>(queryKey, () => getStudyReviews(studyId, size));
};

export default useFetchStudyReviews;
