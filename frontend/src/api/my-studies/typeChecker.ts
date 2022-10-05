import { AxiosError } from 'axios';

import {
  checkType,
  hasOwnProperties,
  hasOwnProperty,
  isArray,
  isDateYMD,
  isNumber,
  isObject,
  isString,
  isStudyStatus,
} from '@utils';

import type { MyStudy, Tag } from '@custom-types';

import { checkMember } from '@api/member/typeChecker';
import { type ApiMyStudies } from '@api/my-studies';

type TagKeys = keyof Pick<Tag, 'id' | 'name'>;

export const checkTag = (data: unknown): Pick<Tag, 'id' | 'name'> => {
  if (!isObject(data)) throw new AxiosError(`Tag does not have correct type: object`);

  const keys: Array<TagKeys> = ['id', 'name'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Tag does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    name: checkType(data.name, isString),
  };
};

type MyStudyKeys = keyof MyStudy;

export const checkMyStudy = (data: unknown): MyStudy => {
  if (!isObject(data)) throw new AxiosError(`MyStudy does not have correct type: object`);

  const keys: Array<MyStudyKeys> = ['id', 'title', 'studyStatus', 'tags', 'owner', 'startDate', 'endDate'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('MyStudy does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    title: checkType(data.title, isString),
    startDate: checkType(data.startDate, isDateYMD),
    endDate: checkType(data.endDate, isDateYMD),
    studyStatus: checkType(data.studyStatus, isStudyStatus),
    tags: checkType(data.tags, isArray).map(tag => checkTag(tag)),
    owner: checkMember(data.owner),
  };
};

export const checkMyStudies = (data: unknown): ApiMyStudies['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`MyStudies does not have correct type: object`);

  if (!hasOwnProperty(data, 'studies')) throw new AxiosError('MyStudies does not have some properties');

  return {
    studies: checkType(data.studies, isArray).map(study => checkMyStudy(study)),
  };
};
