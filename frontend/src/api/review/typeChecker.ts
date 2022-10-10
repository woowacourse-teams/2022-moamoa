import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isDateYMD, isNumber, isObject, isString } from '@utils';

import type { StudyReview } from '@custom-types';

import { checkMember } from '@api/member/typeChecker';

type StudyReviewKeys = keyof StudyReview;

const arrayOfAllStudyReviewKeys = arrayOfAll<StudyReviewKeys>();

export const checkStudyReview = (data: unknown): StudyReview => {
  if (!isObject(data)) throw new AxiosError(`StudyReview does not have correct type: object`);

  const keys = arrayOfAllStudyReviewKeys(['id', 'member', 'createdDate', 'lastModifiedDate', 'content']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('StudyReview does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    member: checkMember(data.member),
    content: checkType(data.content, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};
