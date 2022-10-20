import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isBoolean, isNumber, isObject } from '@utils';

import { checkCommunityComment } from '@api/community/comment/typeChecker';
import { type ApiCommunityComments, ApiInfiniteCommunityComments } from '@api/community/comments';

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
