import { AxiosError } from 'axios';

import {
  arrayOfAll,
  checkType,
  hasOwnProperties,
  isArray,
  isBoolean,
  isNumber,
  isObject,
  isRecruitmentStatus,
  isString,
} from '@utils';

import type { Study, Tag } from '@custom-types';

import { type ApiStudies } from '@api/studies';

type MainStudyTag = Pick<Tag, 'id' | 'name'>;
type MainStudyTagKeys = keyof MainStudyTag;

const arrayOfAllMainStudyTagKeys = arrayOfAll<MainStudyTagKeys>();

const checkMainStudyTag = (data: unknown): MainStudyTag => {
  if (!isObject(data)) throw new AxiosError(`Main-Tag does not have correct type: object`);

  const keys = arrayOfAllMainStudyTagKeys(['id', 'name']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Main-Tag does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    name: checkType(data.name, isString),
  };
};

type StudyKeys = keyof Study;

const arrayOfAllStudyKeys = arrayOfAll<StudyKeys>();

const checkStudy = (data: unknown): Study => {
  if (!isObject(data)) throw new AxiosError(`Main-Study does not have correct type: object`);

  const keys = arrayOfAllStudyKeys(['id', 'excerpt', 'recruitmentStatus', 'tags', 'thumbnail', 'title']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Main-Study does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    title: checkType(data.title, isString),
    excerpt: checkType(data.title, isString),
    thumbnail: checkType(data.title, isString),
    tags: checkType(data.tags, isArray).map(tag => checkMainStudyTag(tag)),
    recruitmentStatus: checkType(data.recruitmentStatus, isRecruitmentStatus),
  };
};

type StudiesKeys = keyof ApiStudies['get']['responseData'];

const arrayOfAllStudiesKeys = arrayOfAll<StudiesKeys>();

export const checkStudies = (data: unknown): ApiStudies['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`Studies does not have correct type: object`);

  const keys = arrayOfAllStudiesKeys(['studies', 'hasNext']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Studies does not have some properties');

  return {
    studies: checkType(data.studies, isArray).map(study => checkStudy(study)),
    hasNext: checkType(data.hasNext, isBoolean),
  };
};
