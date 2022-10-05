import { AxiosError } from 'axios';

import { checkType, hasOwnProperties, isArray, isBoolean, isObject } from '@utils';

import { type ApiStudies } from '@api/studies';
import { checkStudy } from '@api/study/typeChecker';

type StudiesKeys = keyof ApiStudies['get']['responseData'];

export const checkStudies = (data: unknown): ApiStudies['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`Studies does not have correct type: object`);

  const keys: Array<StudiesKeys> = ['studies', 'hasNext'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Studies does not have some properties');

  return {
    studies: checkType(data.studies, isArray).map(study => checkStudy(study)),
    hasNext: checkType(data.hasNext, isBoolean),
  };
};
