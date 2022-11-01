import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isBoolean, isNumber, isObject } from '@utils';

import { checkNoticeComment } from '@api/notice/comment/typeChecker';
import { ApiNoticeComments } from '@api/notice/comments';

type NoticeCommentsKeys = keyof ApiNoticeComments['get']['responseData'];

const arrayOfAllNoticeCommentsKeys = arrayOfAll<NoticeCommentsKeys>();

export const checkNoticeComments = (data: unknown): ApiNoticeComments['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`NoticeComment does not have correct type: object`);

  const keys = arrayOfAllNoticeCommentsKeys(['comments', 'totalCount', 'hasNext']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('NoticeComment does not have some properties');

  return {
    comments: checkType(data.comments, isArray).map(comment => checkNoticeComment(comment)),
    totalCount: checkType(data.totalCount, isNumber),
    hasNext: checkType(data.hasNext, isBoolean),
  };
};
