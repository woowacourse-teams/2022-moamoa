import { AxiosError } from 'axios';

import { checkType, hasOwnProperties, isArray, isBoolean, isDateYMD, isNumber, isObject, isString } from '@utils';

import type { Link } from '@custom-types';

import { type ApiLinks } from '@api/links';
import { checkMember } from '@api/member/typeChecker';

type LinkKeys = keyof Link;

export const checkLink = (data: unknown): Link => {
  if (!isObject(data)) throw new AxiosError(`Link does not have correct type: object`);

  const keys: Array<LinkKeys> = ['id', 'author', 'linkUrl', 'description', 'createdDate', 'lastModifiedDate'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Link does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    author: checkMember(data.author),
    linkUrl: checkType(data.linkUrl, isString),
    description: checkType(data.description, isString),
    createdDate: checkType(data.createdDate, isDateYMD),
    lastModifiedDate: checkType(data.createdDate, isDateYMD),
  };
};

type LinksKeys = keyof ApiLinks['get']['responseData'];

export const checkLinks = (data: unknown): ApiLinks['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`Link does not have correct type: object`);

  const keys: Array<LinksKeys> = ['links', 'hasNext'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Link does not have some properties');

  return {
    links: checkType(data.links, isArray).map(link => checkLink(link)),
    hasNext: checkType(data.hasNext, isBoolean),
  };
};
