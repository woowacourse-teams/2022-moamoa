import { AxiosError } from 'axios';

import {
  arrayOfAll,
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

import { type ApiTags } from '@api/tags';

type TagKeys = keyof Tag;

const arrayOfAllTagKeys = arrayOfAll<TagKeys>();

export const checkTag = (data: unknown): Tag => {
  if (!isObject(data)) throw new AxiosError(`Tag does not have correct type: object`);

  const keys = arrayOfAllTagKeys(['id', 'name', 'description', 'category']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Tag does not have some properties');

  if (!isObject(data.category)) throw new AxiosError(`Tag category does not have correct type: object`);

  const categoryKeys: Array<keyof Tag['category']> = ['id', 'name'];
  if (!hasOwnProperties(data.category, categoryKeys))
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
