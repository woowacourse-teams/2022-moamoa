import { AxiosError } from 'axios';

import {
  arrayOfAll,
  checkType,
  hasOwnProperties,
  hasOwnProperty,
  isNumber,
  isObject,
  isString,
  isUserRole,
} from '@utils';

import type { Member } from '@custom-types';

import { type ApiUserInformation, type ApiUserRole } from '@api/member';

type UserInformationKeys = keyof ApiUserInformation['get']['responseData'];

const arrayOfAllUserInformationKeys = arrayOfAll<UserInformationKeys>();

export const checkUserInformation = (data: unknown): ApiUserInformation['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`UserInformation does not have correct type: object`);

  const keys = arrayOfAllUserInformationKeys(['id', 'username', 'imageUrl', 'profileUrl']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('UserInformation does not have some properties');

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

type MemberKeys = keyof Member;

const arrayOfAllMemberKeys = arrayOfAll<MemberKeys>();

export const checkMember = (data: unknown): Member => {
  if (!isObject(data)) throw new AxiosError(`Member does not have correct type: object`);

  const keys = arrayOfAllMemberKeys(['id', 'username', 'imageUrl', 'profileUrl']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Member does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    username: checkType(data.username, isString),
    imageUrl: checkType(data.imageUrl, isString),
    profileUrl: checkType(data.profileUrl, isString),
  };
};
