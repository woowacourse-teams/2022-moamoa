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

import type { NoticeComment } from '@custom-types';

import { checkMember } from '@api/member/typeChecker';
import { ApiInfiniteNoticeComments, ApiNoticeComments } from '@api/notice/comment';

type NoticeCommentKeys = keyof NoticeComment;

const arrayOfAllNoticeCommentKeys = arrayOfAll<NoticeCommentKeys>();

export const checkNoticeComment = (data: unknown): NoticeComment => {
  if (!isObject(data)) throw new AxiosError(`NoticeComment does not have correct type: object`);

  const keys = arrayOfAllNoticeCommentKeys(['id', 'author', 'createdDate', 'lastModifiedDate', 'content']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('NoticeComment does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    author: checkMember(data.author),
    content: checkType(data.content, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};

// comments

type NoticeCommentsKeys = keyof ApiNoticeComments['get']['responseData'];

const arrayOfAllNoticeCommentsKeys = arrayOfAll<NoticeCommentsKeys>();

export const checkNoticeComments = (data: unknown): ApiNoticeComments['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`NoticeComment does not have correct type: object`);

  const keys = arrayOfAllNoticeCommentsKeys(['comments', 'totalCount']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('NoticeComment does not have some properties');

  return {
    comments: checkType(data.comments, isArray).map(comment => checkNoticeComment(comment)),
    totalCount: checkType(data.totalCount, isNumber),
  };
};

type InfiniteNoticeCommentKeys = keyof ApiInfiniteNoticeComments['get']['responseData'];

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
