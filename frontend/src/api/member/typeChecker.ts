import { AxiosError } from 'axios';

import { checkType, hasOwnProperties, hasOwnProperty, isNumber, isObject, isString, isUserRole } from '@utils';

import type { Member } from '@custom-types';

import { type ApiUserRole } from '@api/member';

type MemberKeys = keyof Member;

export const checkMember = (data: unknown): Member => {
  if (!isObject(data)) throw new AxiosError(`Member does not have correct type: object`);

  const keys: Array<MemberKeys> = ['id', 'username', 'imageUrl', 'profileUrl'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Member does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    username: checkType(data.username, isString),
    imageUrl: checkType(data.imageUrl, isString),
    profileUrl: checkType(data.profileUrl, isString),
  };
};

export const checkUserRole = (data: unknown): ApiUserRole['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`UserRole does not have correct type: object`);

  if (!hasOwnProperty(data, 'role')) throw new AxiosError('UserRole does not have some properties');

  return {
    role: checkType(data.role, isUserRole),
  };
};
