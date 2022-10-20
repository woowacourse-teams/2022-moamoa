import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isBoolean, isNumber, isObject } from '@utils';

import { checkStudyReview } from '@api/review/typeChecker';
import { ApiInfiniteStudyReviews, type ApiStudyReviews } from '@api/reviews';

type StudyReviewsKeys = keyof ApiStudyReviews['get']['responseData'];
type InfinitStudyReviewsKeys = keyof ApiInfiniteStudyReviews['get']['responseData'];

const arrayOfAllStudyReviewsKeys = arrayOfAll<StudyReviewsKeys>();
const arrayOfAllInfiniteStudyReviewsKeys = arrayOfAll<InfinitStudyReviewsKeys>();
export const checkStudyReviews = (data: unknown): ApiStudyReviews['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`StudyReviews does not have correct type: object`);

  const keys = arrayOfAllStudyReviewsKeys(['reviews', 'totalCount']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('StudyReviews does not have some properties');

  return {
    reviews: checkType(data.reviews, isArray).map(review => checkStudyReview(review)),
    totalCount: checkType(data.totalCount, isNumber),
  };
};

export const checkStudyReviewsWithPage = (data: unknown): ApiInfiniteStudyReviews['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`StudyReviews does not have correct type: object`);

  const keys = arrayOfAllInfiniteStudyReviewsKeys(['reviews', 'totalCount', 'hasNext']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('StudyReviews does not have some properties');

  return {
    reviews: checkType(data.reviews, isArray).map(review => checkStudyReview(review)),
    totalCount: checkType(data.totalCount, isNumber),
    hasNext: checkType(data.hasNext, isBoolean),
  };
};
