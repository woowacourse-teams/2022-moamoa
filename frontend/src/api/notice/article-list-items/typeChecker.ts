import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isDateYMD, isNumber, isObject, isString } from '@utils';

import { NoticeArticleDetail } from '@custom-types';

import { checkMember } from '@api/member/typeChecker';
import { ApiNoticeArticleListItems } from '@api/notice/article-list-items';

// list item
type NoticeArticleListItem = Omit<NoticeArticleDetail, 'content'>;
type NoticeArticleListItemKeys = keyof NoticeArticleListItem;

const arrayOfAllArticleListItemKeys = arrayOfAll<NoticeArticleListItemKeys>();

const checkNoticeArticleListItem = (data: unknown): NoticeArticleListItem => {
  if (!isObject(data)) throw new AxiosError(`NoticeArticles-Article does not have correct type: object`);

  const keys = arrayOfAllArticleListItemKeys(['id', 'author', 'title', 'createdDate', 'lastModifiedDate']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('NoticeArticles-Article does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    author: checkMember(data.author),
    title: checkType(data.title, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};

type NoticeArticeListItemsKeys = keyof ApiNoticeArticleListItems['get']['responseData'];
const arrayOfAllNoticeArticleListItemsKeys = arrayOfAll<NoticeArticeListItemsKeys>();

export const checkNoticeArticleListItems = (data: unknown): ApiNoticeArticleListItems['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`CommunityArticles does not have correct type: object`);

  const keys = arrayOfAllNoticeArticleListItemsKeys(['articles', 'currentPage', 'lastPage', 'totalCount']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityArticles does not have some properties');

  return {
    articles: checkType(data.articles, isArray).map(article => checkNoticeArticleListItem(article)),
    currentPage: checkType(data.currentPage, isNumber) + 1,
    lastPage: checkType(data.lastPage, isNumber) + 1,
    totalCount: checkType(data.totalCount, isNumber),
  };
};
