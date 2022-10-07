import { AxiosError } from 'axios';

import { checkType, hasOwnProperty, isArray, isObject } from '@utils';

import { type ApiMyStudies } from '@api/my-studies';
import { checkMyStudy } from '@api/my-study/typeChecker';

export const checkMyStudies = (data: unknown): ApiMyStudies['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`MyStudies does not have correct type: object`);

  if (!hasOwnProperty(data, 'studies')) throw new AxiosError('MyStudies does not have some properties');

  return {
    studies: checkType(data.studies, isArray).map(study => checkMyStudy(study)),
  };
};
