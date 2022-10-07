import { AxiosError } from 'axios';

import { checkType, hasOwnProperties, isArray, isDateYMD, isNumber, isObject, isString } from '@utils';

import { type ApiCommunityArticle, type ApiCommunityArticles } from '@api/community';
import { checkMember } from '@api/member/typeChecker';

type CommunityArticleKeys = keyof ApiCommunityArticle['get']['responseData'];

export const checkCommunityArticle = (data: unknown): ApiCommunityArticle['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`CommunityArticle does not have correct type: object`);

  const keys: Array<CommunityArticleKeys> = ['id', 'author', 'title', 'content', 'createdDate', 'lastModifiedDate'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityArticle does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    author: checkMember(data.author),
    title: checkType(data.title, isString),
    content: checkType(data.content, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};

type CommunityArticlesKeys = keyof ApiCommunityArticles['get']['responseData'];

export const checkCommunityArticles = (data: unknown): ApiCommunityArticles['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`CommunityArticles does not have correct type: object`);

  const keys: Array<CommunityArticlesKeys> = ['articles', 'currentPage', 'lastPage', 'totalCount'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityArticles does not have some properties');

  return {
    articles: checkType(data.articles, isArray).map(article => checkCommunityArticle(article)),
    currentPage: checkType(data.currentPage, isNumber) + 1,
    lastPage: checkType(data.lastPage, isNumber),
    totalCount: checkType(data.totalCount, isNumber),
  };
};
