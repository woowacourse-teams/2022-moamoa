import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isDateYMD, isNumber, isObject, isString } from '@utils';

import type { NoticeComment } from '@custom-types';

import { checkMember } from '@api/member/typeChecker';

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
