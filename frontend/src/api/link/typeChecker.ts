import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isDateYMD, isNumber, isObject, isString } from '@utils';

import type { Link } from '@custom-types';

import { checkMember } from '@api/member/typeChecker';

type LinkKeys = keyof Link;

const arrayOfAllLinkKeys = arrayOfAll<LinkKeys>();

export const checkLink = (data: unknown): Link => {
  if (!isObject(data)) throw new AxiosError(`Link does not have correct type: object`);

  const keys = arrayOfAllLinkKeys(['id', 'author', 'linkUrl', 'description', 'createdDate', 'lastModifiedDate']);
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
