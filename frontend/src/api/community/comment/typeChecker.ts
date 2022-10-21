import { AxiosError } from 'axios';

import {
  arrayOfAll,
  checkType,
  hasOwnProperties,
  isArray,
  isBoolean,
  isDateYMD,
  isNumber,
  isObject,
  isString,
} from '@utils';

import type { CommunityComment } from '@custom-types';

import { ApiCommunityComments, ApiInfiniteCommunityComments } from '@api/community/comment';
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

type CommunityCommentsKeys = keyof ApiCommunityComments['get']['responseData'];
type InfiniteCommunityCommentKeys = keyof ApiInfiniteCommunityComments['get']['responseData'];

const arrayOfAllCommunityCommentsKeys = arrayOfAll<CommunityCommentsKeys>();
export const checkCommunityComments = (data: unknown): ApiCommunityComments['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`CommunityComments does not have correct type: object`);

  const keys = arrayOfAllCommunityCommentsKeys(['comments', 'totalCount']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityComments does not have some properties');

  return {
    comments: checkType(data.comments, isArray).map(comment => checkCommunityComment(comment)),
    totalCount: checkType(data.totalCount, isNumber),
  };
};

// comments

const arrayOfAllInfiniteCommunityCommentsKeys = arrayOfAll<InfiniteCommunityCommentKeys>();
export const checkInfiniteCommunityComments = (data: unknown): ApiInfiniteCommunityComments['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`InfiniteCommunityComment does not have correct type: object`);

  const keys = arrayOfAllInfiniteCommunityCommentsKeys(['comments', 'totalCount', 'hasNext']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('InfiniteCommunityComment does not have some properties');

  return {
    comments: checkType(data.comments, isArray).map(comment => checkCommunityComment(comment)),
    totalCount: checkType(data.totalCount, isNumber),
    hasNext: checkType(data.hasNext, isBoolean),
  };
};
