import { AxiosError } from 'axios';

import {
  arrayOfAll,
  checkType,
  hasOwnProperties,
  hasOwnProperty,
  isDateYMD,
  isNumber,
  isObject,
  isString,
} from '@utils';

import { type ApiCommunityDraftArticle } from '@api/community/draft-article';

type CommunityDraftArticleKeys = keyof ApiCommunityDraftArticle['get']['responseData'];

const arrayOfAllCommunityDraftArticleKeys = arrayOfAll<CommunityDraftArticleKeys>();

export const checkCommunityDraftArticle = (data: unknown): ApiCommunityDraftArticle['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`CommunityDraftArticle does not have correct type: object`);

  const keys = arrayOfAllCommunityDraftArticleKeys(['id', 'title', 'content', 'createdDate', 'lastModifiedDate']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('CommunityDraftArticle does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    title: checkType(data.title, isString),
    content: checkType(data.content, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.lastModifiedDate, isDateYMD),
  };
};

export const checkDraftArticleId = (data: unknown): ApiCommunityDraftArticle['post']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`DraftArticleId does not have correct type: object`);

  if (!hasOwnProperty(data, 'draftArticleId'))
    throw new AxiosError('CommunityDraftArticle does not have some properties');

  return {
    draftArticleId: checkType(data.draftArticleId, isNumber),
  };
};
