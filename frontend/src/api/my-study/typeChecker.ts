import { AxiosError } from 'axios';

import {
  arrayOfAll,
  checkType,
  hasOwnProperties,
  isArray,
  isDateYMD,
  isNumber,
  isObject,
  isString,
  isStudyStatus,
} from '@utils';

import type { MyStudy, Tag } from '@custom-types';

import { checkMember } from '@api/member/typeChecker';

type MyStudyTag = Pick<Tag, 'id' | 'name'>;
type MyStudyTagKeys = keyof MyStudyTag;

const arrayOfAllMyStudyTagKeys = arrayOfAll<MyStudyTagKeys>();

const checkMyStudyTag = (data: unknown): MyStudyTag => {
  if (!isObject(data)) throw new AxiosError(`Tag does not have correct type: object`);

  const keys = arrayOfAllMyStudyTagKeys(['id', 'name']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Tag does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    name: checkType(data.name, isString),
  };
};

type MyStudyKeys = keyof MyStudy;

const arrayOfAllMyStudyKeys = arrayOfAll<MyStudyKeys>();

export const checkMyStudy = (data: unknown): MyStudy => {
  if (!isObject(data)) throw new AxiosError(`MyStudy does not have correct type: object`);

  const keys = arrayOfAllMyStudyKeys(['id', 'title', 'studyStatus', 'tags', 'owner', 'startDate', 'endDate']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('MyStudy does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    title: checkType(data.title, isString),
    startDate: checkType(data.startDate, isDateYMD),
    endDate: checkType(data.endDate, isDateYMD),
    studyStatus: checkType(data.studyStatus, isStudyStatus),
    tags: checkType(data.tags, isArray).map(tag => checkMyStudyTag(tag)),
    owner: checkMember(data.owner),
  };
};
