import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isNumber, isObject } from '@utils';

import { checkStudyReview } from '@api/review/typeChecker';
import { type ApiStudyReviews } from '@api/reviews';

type StudyReviewsKeys = keyof ApiStudyReviews['get']['responseData'];

const arrayOfAllStudyReviewsKeys = arrayOfAll<StudyReviewsKeys>();
export const checkStudyReviews = (data: unknown): ApiStudyReviews['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`StudyReviews does not have correct type: object`);

  const keys = arrayOfAllStudyReviewsKeys(['reviews', 'totalCount']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('StudyReviews does not have some properties');

  return {
    reviews: checkType(data.reviews, isArray).map(review => checkStudyReview(review)),
    totalCount: checkType(data.totalCount, isNumber),
  };
};
