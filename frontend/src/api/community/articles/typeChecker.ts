import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isDateYMD, isNumber, isObject, isString } from '@utils';

import type { CommunityArticle } from '@custom-types';

import { type ApiCommunityArticles } from '@api/community/articles';
import { checkMember } from '@api/member/typeChecker';

type Article = Omit<CommunityArticle, 'content'>;
type ArticleKeys = keyof Article;

const arrayOfAllArticleKeys = arrayOfAll<ArticleKeys>();

const checkArticleListItem = (data: unknown): Article => {
  if (!isObject(data)) throw new AxiosError(`CommunityArticles-Article does not have correct type: object`);

  const keys = arrayOfAllArticleKeys(['id', 'author', 'title', 'createdDate', 'lastModifiedDate']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityArticles-Article does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    author: checkMember(data.author),
    title: checkType(data.title, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};

type CommunityArticlesKeys = keyof ApiCommunityArticles['get']['responseData'];

const arrayOfAllCommunityArticlesKeys = arrayOfAll<CommunityArticlesKeys>();

export const checkCommunityArticles = (data: unknown): ApiCommunityArticles['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`CommunityArticles does not have correct type: object`);

  const keys = arrayOfAllCommunityArticlesKeys(['articles', 'currentPage', 'lastPage', 'totalCount']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityArticles does not have some properties');

  return {
    articles: checkType(data.articles, isArray).map(article => checkArticleListItem(article)),
    currentPage: checkType(data.currentPage, isNumber) + 1,
    lastPage: checkType(data.lastPage, isNumber) + 1,
    totalCount: checkType(data.totalCount, isNumber),
  };
};
