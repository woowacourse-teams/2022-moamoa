import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isDateYMD, isNumber, isObject, isString } from '@utils';

import type { NoticeArticle } from '@custom-types';

import { checkMember } from '@api/member/typeChecker';
import { type ApiNoticeArticle, ApiNoticeArticles } from '@api/notice/article';

type NoticeArticleKeys = keyof ApiNoticeArticle['get']['responseData'];

const arrayOfAllNoticeArticleKeys = arrayOfAll<NoticeArticleKeys>();

export const checkNoticeArticle = (data: unknown): ApiNoticeArticle['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`NoticeArticle does not have correct type: object`);

  const keys = arrayOfAllNoticeArticleKeys(['id', 'author', 'title', 'content', 'createdDate', 'lastModifiedDate']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('NoticeArticle does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    author: checkMember(data.author),
    title: checkType(data.title, isString),
    content: checkType(data.content, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};

type Article = Omit<NoticeArticle, 'content'>;
type ArticleKeys = keyof Article;

const arrayOfAllArticleKeys = arrayOfAll<ArticleKeys>();

const checkArticle = (data: unknown): Article => {
  if (!isObject(data)) throw new AxiosError(`NoticeArticles-Article does not have correct type: object`);

  const keys = arrayOfAllArticleKeys(['id', 'author', 'title', 'createdDate', 'lastModifiedDate']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('NoticeArticles-Article does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    author: checkMember(data.author),
    title: checkType(data.title, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};

// articles
type NoticeArticlesKeys = keyof ApiNoticeArticles['get']['responseData'];

const arrayOfAllNoticeArticlesKeys = arrayOfAll<NoticeArticlesKeys>();

export const checkNoticeArticles = (data: unknown): ApiNoticeArticles['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`NoticeArticles does not have correct type: object`);

  const keys = arrayOfAllNoticeArticlesKeys(['articles', 'currentPage', 'lastPage', 'totalCount']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('NoticeArticles does not have some properties');

  return {
    articles: checkType(data.articles, isArray).map(article => checkArticle(article)),
    currentPage: checkType(data.currentPage, isNumber) + 1,
    lastPage: checkType(data.lastPage, isNumber) + 1,
    totalCount: checkType(data.totalCount, isNumber),
  };
};
