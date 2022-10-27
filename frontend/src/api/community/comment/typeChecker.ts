import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isDateYMD, isNumber, isObject, isString } from '@utils';

import type { CommunityComment } from '@custom-types';

import { checkMember } from '@api/member/typeChecker';

type CommunityCommentKeys = keyof CommunityComment;

const arrayOfAllCommunityCommentKeys = arrayOfAll<CommunityCommentKeys>();

export const checkCommunityComment = (data: unknown): CommunityComment => {
  if (!isObject(data)) throw new AxiosError(`CommunityComment does not have correct type: object`);

  const keys = arrayOfAllCommunityCommentKeys(['id', 'author', 'createdDate', 'lastModifiedDate', 'content']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityComment does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    author: checkMember(data.author),
    content: checkType(data.content, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};
