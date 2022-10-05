import { AxiosError } from 'axios';

import { checkType, hasOwnProperties, isArray, isDateYMD, isNumber, isObject, isString } from '@utils';

import type { StudyReview } from '@custom-types';

import { checkMember } from '@api/member/typeChecker';
import { ApiReviews } from '@api/reviews';

type StudyReviewKeys = keyof StudyReview;

export const checkStudyReview = (data: unknown): StudyReview => {
  if (!isObject(data)) throw new AxiosError(`StudyReview does not have correct type: object`);

  const keys: Array<StudyReviewKeys> = ['id', 'member', 'createdDate', 'lastModifiedDate', 'content'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('StudyReview does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    member: checkMember(data.member),
    content: checkType(data.content, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};

type StudyReviewsKeys = keyof ApiReviews['get']['responseData'];

export const checkStudyReviews = (data: unknown): ApiReviews['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`StudyReviews does not have correct type: object`);

  const keys: Array<StudyReviewsKeys> = ['reviews', 'totalCount'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('StudyReviews does not have some properties');

  return {
    reviews: checkType(data.reviews, isArray).map(review => checkStudyReview(review)),
    totalCount: checkType(data.totalCount, isNumber),
  };
};
