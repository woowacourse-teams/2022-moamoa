import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isBoolean, isNumber, isObject } from '@utils';

import { checkNoticeComment } from '@api/notice-comment/typeChecker';
import { type ApiInfiniteNoticeComments, type ApiNoticeComments } from '@api/notice-comments';

type NoticeCommentKeys = keyof ApiNoticeComments['get']['responseData'];
type InfiniteNoticeCommentKeys = keyof ApiInfiniteNoticeComments['get']['responseData'];

const arrayOfAllNoticeCommentsKeys = arrayOfAll<NoticeCommentKeys>();
export const checkNoticeComments = (data: unknown): ApiNoticeComments['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`NoticeComment does not have correct type: object`);

  const keys = arrayOfAllNoticeCommentsKeys(['comments', 'totalCount']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('NoticeComment does not have some properties');

  return {
    comments: checkType(data.comments, isArray).map(comment => checkNoticeComment(comment)),
    totalCount: checkType(data.totalCount, isNumber),
  };
};

const arrayOfAllInfiniteNoticeCommentsKeys = arrayOfAll<InfiniteNoticeCommentKeys>();
export const checkInfiniteNoticeComments = (data: unknown): ApiInfiniteNoticeComments['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`InfiniteNoticeComment does not have correct type: object`);

  const keys = arrayOfAllInfiniteNoticeCommentsKeys(['comments', 'totalCount', 'hasNext']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('InfiniteNoticeComment does not have some properties');

  return {
    comments: checkType(data.comments, isArray).map(comment => checkNoticeComment(comment)),
    totalCount: checkType(data.totalCount, isNumber),
    hasNext: checkType(data.hasNext, isBoolean),
  };
};
