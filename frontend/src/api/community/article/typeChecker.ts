import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isDateYMD, isNumber, isObject, isString } from '@utils';

import { type ApiCommunityArticle } from '@api/community/article';
import { checkMember } from '@api/member/typeChecker';

type CommunityArticleKeys = keyof ApiCommunityArticle['get']['responseData'];

const arrayOfAllCommunityArticleKeys = arrayOfAll<CommunityArticleKeys>();

export const checkCommunityArticle = (data: unknown): ApiCommunityArticle['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`CommunityArticle does not have correct type: object`);

  const keys = arrayOfAllCommunityArticleKeys(['id', 'author', 'title', 'content', 'createdDate', 'lastModifiedDate']);
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
