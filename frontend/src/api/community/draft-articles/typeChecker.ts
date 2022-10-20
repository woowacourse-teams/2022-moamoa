import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isDateYMD, isNumber, isObject, isString } from '@utils';

import type { DraftArtcle, StudyDetail, StudyId } from '@custom-types';

import { ApiCommunityDraftArticles } from '.';

type Article = Omit<DraftArtcle, 'content'> & {
  study: {
    id: StudyId;
    title: StudyDetail['title'];
  };
};
type ArticleKeys = keyof Article;

const arrayOfAllArticleKeys = arrayOfAll<ArticleKeys>();

const checkArticle = (data: unknown): Article => {
  if (!isObject(data)) throw new AxiosError(`CommunityDraftArticles-Article does not have correct type: object`);

  const keys = arrayOfAllArticleKeys(['id', 'title', 'createdDate', 'lastModifiedDate', 'study']);
  if (!hasOwnProperties(data, keys))
    throw new AxiosError('CommunityDraftArticles-Article does not have some properties');

  if (!isObject(data.study))
    throw new AxiosError(`CommunityDraftArticles-Article-study does not have correct type: object`);

  if (!hasOwnProperties(data.study, ['id', 'title']))
    throw new AxiosError('CommunityDraftArticles-Article-study does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    title: checkType(data.title, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
    study: {
      id: checkType(data.study.id, isNumber),
      title: checkType(data.study.title, isString),
    },
  };
};

type CommunityDraftArticlesKeys = keyof ApiCommunityDraftArticles['get']['responseData'];

const arrayOfAllCommunityArticlesKeys = arrayOfAll<CommunityDraftArticlesKeys>();

export const checkCommunityDraftArticles = (data: unknown): ApiCommunityDraftArticles['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`CommunityDraftArticles does not have correct type: object`);

  const keys = arrayOfAllCommunityArticlesKeys(['draftArticles', 'currentPage', 'lastPage', 'totalCount']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityDraftArticles does not have some properties');

  return {
    draftArticles: checkType(data.draftArticles, isArray).map(article => checkArticle(article)),
    currentPage: checkType(data.currentPage, isNumber) + 1,
    lastPage: checkType(data.lastPage, isNumber) + 1,
    totalCount: checkType(data.totalCount, isNumber),
  };
};
