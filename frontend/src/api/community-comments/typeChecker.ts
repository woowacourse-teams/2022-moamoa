import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isNumber, isObject } from '@utils';

import { checkCommunityComment } from '@api/community-comment/typeChecker';
import { type ApiCommunityComments } from '@api/community-comments';

type CommunityCommentsKeys = keyof ApiCommunityComments['get']['responseData'];

const arrayOfAllCommunityCommentsKeys = arrayOfAll<CommunityCommentsKeys>();
export const checkCommunityComments = (data: unknown): ApiCommunityComments['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`CommunityComments does not have correct type: object`);

  const keys = arrayOfAllCommunityCommentsKeys(['comments', 'totalCount']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityComments does not have some properties');

  return {
    comments: checkType(data.comments, isArray).map(review => checkCommunityComment(review)),
    totalCount: checkType(data.totalCount, isNumber),
  };
};
