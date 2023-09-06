import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isDateYMD, isNumber, isObject, isString } from '@utils';

import type { CommunityArticleDetail } from '@custom-types';

import { type ApiCommunityArticleList } from '@api/community/article-list-items';
import { checkMember } from '@api/member/typeChecker';

type CommunityArticleListItem = Omit<CommunityArticleDetail, 'content'>;
type CommunityArticleListItemKeys = keyof CommunityArticleListItem;

const arrayOfAllCommunityArticleListItemKeys = arrayOfAll<CommunityArticleListItemKeys>();

const checkCommunityArticleListItem = (data: unknown): CommunityArticleListItem => {
  if (!isObject(data)) throw new AxiosError(`CommunityArticleListItem does not have correct type: object`);

  const keys = arrayOfAllCommunityArticleListItemKeys(['id', 'author', 'title', 'createdDate', 'lastModifiedDate']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityArticleListItem does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    author: checkMember(data.author),
    title: checkType(data.title, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};

type CommunityArticeListItemsKeys = keyof ApiCommunityArticleList['get']['responseData'];

// @TODO: CommunityArticleListItemsKeys로 변경
const arrayOfAllCommunityArticlesKeys = arrayOfAll<CommunityArticeListItemsKeys>();

export const checkCommunityArticleListItems = (data: unknown): ApiCommunityArticleList['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`CommunityArticles does not have correct type: object`);

  const keys = arrayOfAllCommunityArticlesKeys(['articles', 'currentPage', 'lastPage', 'totalCount']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityArticles does not have some properties');

  return {
    articles: checkType(data.articles, isArray).map(article => checkCommunityArticleListItem(article)),
    currentPage: checkType(data.currentPage, isNumber) + 1,
    lastPage: checkType(data.lastPage, isNumber) + 1,
    totalCount: checkType(data.totalCount, isNumber),
  };
};