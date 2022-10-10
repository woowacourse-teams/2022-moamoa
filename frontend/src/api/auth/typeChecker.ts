import { AxiosError } from 'axios';

import { checkType, hasOwnProperties, isNumber, isObject, isString } from '@utils';

import { type ApiLogin, type ApiRefresh } from '@api/auth';

type LoginKeys = keyof ApiLogin['post']['responseData'];

export const checkLogin = (data: unknown): ApiLogin['post']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`Login does not have correct type: object`);

  const keys: Array<LoginKeys> = ['accessToken', 'expiredTime'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Login does not have some properties');

  return {
    accessToken: checkType(data.accessToken, isString),
    expiredTime: checkType(data.expiredTime, isNumber),
  };
};

type RefreshKeys = keyof ApiRefresh['get']['responseData'];

export const checkRefresh = (data: unknown): ApiRefresh['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`Refresh does not have correct type: object`);

  const keys: Array<RefreshKeys> = ['accessToken', 'expiredTime'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Refresh does not have some properties');

  return {
    accessToken: checkType(data.accessToken, isString),
    expiredTime: checkType(data.expiredTime, isNumber),
  };
};
