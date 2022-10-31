import { useQuery } from 'react-query';

import { DEFAULT_STUDY_REVIEW_QUERY_PARAM } from '@constants';

import { buildURLQuery } from '@utils';

import type { Size, StudyId, StudyReview } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkStudyReviews } from '@api/reviews/typeChecker';

export const QK_STUDY_REVIEWS = 'study-reviews';
export const QK_STUDY_REVIEWS_INFINITE_SCROLL = 'infinite-scroll-study-reviews';

export type ApiStudyReviews = {
  get: {
    params: {
      studyId: StudyId;
      size?: Size;
    };
    responseData: {
      reviews: Array<StudyReview>;
      totalCount: number;
    };
    variables: ApiStudyReviews['get']['params'];
  };
};

const { SIZE } = DEFAULT_STUDY_REVIEW_QUERY_PARAM;

// size와 page가 없는 경우에는 전체를 불러온다
// reviews는 pagination이 아닌 전체를 불러와서 사용하는 경우도 있기 때문에
// getStudyReviews와 getStudyReviewsWithPage를 구분해 줘야 합니다.
const getStudyReviews = async ({ studyId, size }: ApiStudyReviews['get']['variables']) => {
  const url = buildURLQuery(`/api/studies/${studyId}/reviews`, {
    size,
  });
  const response = await axiosInstance.get<ApiStudyReviews['get']['responseData']>(url);
  return checkStudyReviews(response.data);
};
export const useGetStudyReviews = ({ size = SIZE, studyId }: ApiStudyReviews['get']['variables']) => {
  return useQuery([QK_STUDY_REVIEWS, size, studyId], () => getStudyReviews({ size, studyId }));
};
