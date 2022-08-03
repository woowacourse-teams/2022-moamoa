import { useQuery } from 'react-query';

import { QK_FETCH_STUDY_REVIEWS } from '@constants';

import type { GetReviewResponseData } from '@custom-types';

import { getStudyReviews } from '@api';

const useGetStudyReviews = (studyId: number, size?: number) => {
  const queryKey = size ? [QK_FETCH_STUDY_REVIEWS, studyId] : [QK_FETCH_STUDY_REVIEWS, studyId, 'all'];
  return useQuery<GetReviewResponseData, Error>(queryKey, () => getStudyReviews({ studyId, size }));
};

export default useGetStudyReviews;
