import { QK_FETCH_STUDY_REVIEWS } from '@constants';
import { useQuery } from 'react-query';

import getStudyReviews from '@api/getStudyReviews';
import type { StudyReviewResponse } from '@api/getStudyReviews';

const useFetchStudyReviews = (studyId: string, size: number, loadAll = false) => {
  const queryKey = loadAll ? [QK_FETCH_STUDY_REVIEWS, studyId, 'all'] : [QK_FETCH_STUDY_REVIEWS, studyId];
  return useQuery<StudyReviewResponse, unknown>(queryKey, () => getStudyReviews(studyId, size, loadAll));
};

export default useFetchStudyReviews;
