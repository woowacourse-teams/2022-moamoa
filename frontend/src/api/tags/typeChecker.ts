import { AxiosError } from 'axios';

import {
  checkType,
  hasOwnProperties,
  hasOwnProperty,
  isArray,
  isCategoryName,
  isNumber,
  isObject,
  isString,
} from '@utils';

import type { Tag } from '@custom-types';

import { ApiTags } from '.';

type TagKeys = keyof Tag;

export const checkTag = (data: unknown): Tag => {
  if (!isObject(data)) throw new AxiosError(`Tag does not have correct type: object`);

  const keys: Array<TagKeys> = ['id', 'name', 'description', 'category'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Tag does not have some properties');

  if (!isObject(data.category)) throw new AxiosError(`Tag category does not have correct type: object`);

  if (!hasOwnProperties(data.category, ['id', 'name']))
    throw new AxiosError('Tag category does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    name: checkType(data.name, isString),
    description: checkType(data.description, isString),
    category: {
      id: checkType(data.category.id, isNumber),
      name: checkType(data.category.name, isCategoryName),
    },
  };
};

export const checkTags = (data: unknown): ApiTags['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`Tags does not have correct type: object`);

  if (!hasOwnProperty(data, 'tags')) throw new AxiosError('Tags does not have some properties');

  return {
    tags: checkType(data.tags, isArray).map(tag => checkTag(tag)),
  };
};
