import { AxiosError } from 'axios';

import {
  checkType,
  hasOwnProperties,
  isArray,
  isDateYMD,
  isNumber,
  isObject,
  isRecruitmentStatus,
  isString,
} from '@utils';

import type { DateYMD, Member } from '@custom-types';

import { type ApiStudy } from '@api/study';
import { checkTag } from '@api/tags/typeChecker';

type StudyMember = Member & { participationDate: DateYMD; numberOfStudy: number };
type StudyMemberKeys = keyof StudyMember;

const checkStudyMember = (data: unknown): StudyMember => {
  if (!isObject(data)) throw new AxiosError(`StudyMember does not have correct type: object`);

  const keys: Array<StudyMemberKeys> = [
    'id',
    'username',
    'imageUrl',
    'profileUrl',
    'participationDate',
    'numberOfStudy',
  ];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('StudyMember does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    username: checkType(data.username, isString),
    imageUrl: checkType(data.imageUrl, isString),
    profileUrl: checkType(data.profileUrl, isString),
    participationDate: checkType(data.participationDate, isDateYMD),
    numberOfStudy: checkType(data.numberOfStudy, isNumber),
  };
};

type StudyKeys = keyof ApiStudy['get']['responseData'];

export const checkStudy = (data: unknown): ApiStudy['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`Study does not have correct type: object`);

  const keys: Array<StudyKeys> = [
    'id',
    'title',
    'excerpt',
    'thumbnail',
    'recruitmentStatus',
    'description',
    'currentMemberCount',
    'maxMemberCount',
    'createdDate',
    'enrollmentEndDate',
    'endDate',
    'startDate',
    'owner',
    'members',
    'tags',
  ];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Study does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    title: checkType(data.title, isString),
    excerpt: checkType(data.excerpt, isString),
    thumbnail: checkType(data.thumbnail, isString),
    recruitmentStatus: checkType(data.recruitmentStatus, isRecruitmentStatus),
    description: checkType(data.description, isString),
    currentMemberCount: checkType(data.id, isNumber),
    maxMemberCount: checkType(data.maxMemberCount, isNumber, true),
    createdDate: checkType(data.createdDate, isDateYMD),
    enrollmentEndDate: checkType(data.enrollmentEndDate, isDateYMD, true),
    startDate: checkType(data.startDate, isDateYMD),
    endDate: checkType(data.endDate, isDateYMD, true),
    owner: checkStudyMember(data.owner),
    members: checkType(data.members, isArray).map(member => checkStudyMember(member)),
    tags: checkType(data.tags, isArray).map(tag => checkTag(tag)),
  };
};
