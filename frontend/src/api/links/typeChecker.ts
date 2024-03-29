import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isArray, isBoolean, isObject } from '@utils';

import { checkLink } from '@api/link/typeChecker';
import { type ApiLinks } from '@api/links';

type LinksKeys = keyof ApiLinks['get']['responseData'];

const arrayOfAllLinksKeys = arrayOfAll<LinksKeys>();

export const checkLinks = (data: unknown): ApiLinks['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`Links does not have correct type: object`);

  const keys = arrayOfAllLinksKeys(['links', 'hasNext']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Links does not have some properties');

  return {
    links: checkType(data.links, isArray).map(link => checkLink(link)),
    hasNext: checkType(data.hasNext, isBoolean),
  };
};
