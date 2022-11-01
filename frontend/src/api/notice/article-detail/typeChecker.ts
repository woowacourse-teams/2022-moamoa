import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isDateYMD, isNumber, isObject, isString } from '@utils';

import { checkMember } from '@api/member/typeChecker';
import { type ApiNoticeArticleDetail } from '@api/notice/article-detail';

type NoticeArticleKeys = keyof ApiNoticeArticleDetail['get']['responseData'];

const arrayOfAllNoticeArticleKeys = arrayOfAll<NoticeArticleKeys>();

export const checkNoticeArticle = (data: unknown): ApiNoticeArticleDetail['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`NoticeArticleDetail does not have correct type: object`);

  const keys = arrayOfAllNoticeArticleKeys(['id', 'author', 'title', 'content', 'createdDate', 'lastModifiedDate']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('NoticeArticleDetail does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    author: checkMember(data.author),
    title: checkType(data.title, isString),
    content: checkType(data.content, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};
