import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isDateYMD, isNumber, isObject, isString } from '@utils';

import { type ApiCommunityArticleDetail } from '@api/community/article-detail';
import { checkMember } from '@api/member/typeChecker';

type CommunityArticleDetailKeys = keyof ApiCommunityArticleDetail['get']['responseData'];

const arrayOfAllCommunityArticleDetailKeys = arrayOfAll<CommunityArticleDetailKeys>();

export const checkCommunityArticleDetail = (data: unknown): ApiCommunityArticleDetail['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`CommunityArticle does not have correct type: object`);

  const keys = arrayOfAllCommunityArticleDetailKeys([
    'id',
    'author',
    'title',
    'content',
    'createdDate',
    'lastModifiedDate',
  ]);
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
