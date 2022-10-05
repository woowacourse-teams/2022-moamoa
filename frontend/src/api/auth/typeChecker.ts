import { AxiosError } from 'axios';

import { checkType, hasOwnProperties, isNumber, isObject, isString } from '@utils';

import { type ApiLogin, type ApiRefreshToken } from '@api/auth';

type LoginKeys = keyof ApiLogin['post']['responseData'];

export const checkLogin = (data: unknown): ApiRefreshToken['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`Token does not have correct type: object`);

  const keys: Array<LoginKeys> = ['accessToken', 'expiredTime'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('StudyReviews does not have some properties');

  return {
    accessToken: checkType(data.accessToken, isString),
    expiredTime: checkType(data.expiredTime, isNumber),
  };
};

type RefreshTokenKeys = keyof ApiRefreshToken['get']['responseData'];

export const checkRefresh = (data: unknown): ApiRefreshToken['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`Token does not have correct type: object`);

  const keys: Array<RefreshTokenKeys> = ['accessToken', 'expiredTime'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('StudyReviews does not have some properties');

  return {
    accessToken: checkType(data.accessToken, isString),
    expiredTime: checkType(data.expiredTime, isNumber),
  };
};
