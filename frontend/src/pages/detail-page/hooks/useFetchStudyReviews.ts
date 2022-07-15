import { QK_FETCH_STUDY_REVIEWS } from '@constants';
import { useQuery } from 'react-query';

import { StudyReview } from '@custom-types/index';

import getStudyReviews from '@api/getStudyReviews';

const useFetchStudyReviews = (studyId: string) => {
  return useQuery<{ reviews: Array<StudyReview> }, unknown>([QK_FETCH_STUDY_REVIEWS, studyId], () =>
    getStudyReviews(studyId),
  );
};

export default useFetchStudyReviews;
