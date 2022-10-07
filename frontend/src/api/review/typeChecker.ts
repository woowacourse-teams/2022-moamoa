import { AxiosError } from 'axios';

import { checkType, hasOwnProperties, isDateYMD, isNumber, isObject, isString } from '@utils';

import type { StudyReview } from '@custom-types';

import { checkMember } from '@api/member/typeChecker';

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
