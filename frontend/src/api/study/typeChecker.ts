import { AxiosError } from 'axios';

import {
  checkOptionalType,
  checkType,
  hasOwnProperties,
  isArray,
  isDateYMD,
  isNumber,
  isObject,
  isRecruitmentStatus,
  isString,
} from '@utils';

import type { DateYMD, Member, StudyDetail } from '@custom-types';

import { checkTag } from '@api/tags/typeChecker';

type MemberKeys = keyof (Member & { participationDate: DateYMD; numberOfStudy: number });

export const checkMember = (data: unknown): Member & { participationDate: DateYMD; numberOfStudy: number } => {
  if (!isObject(data)) throw new AxiosError(`Member does not have correct type: object`);

  const keys: Array<MemberKeys> = ['id', 'username', 'imageUrl', 'profileUrl', 'participationDate', 'numberOfStudy'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('Member does not have some properties');

  return {
    id: checkType(data.id, isNumber),
    username: checkType(data.username, isString),
    imageUrl: checkType(data.imageUrl, isString),
    profileUrl: checkType(data.profileUrl, isString),
    participationDate: checkType(data.participationDate, isDateYMD),
    numberOfStudy: checkType(data.numberOfStudy, isNumber),
  };
};

type StudyKeys = keyof StudyDetail;

export const checkStudy = (data: unknown): StudyDetail => {
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
    maxMemberCount: checkOptionalType(data.maxMemberCount, isNumber),
    createdDate: checkType(data.createdDate, isDateYMD),
    enrollmentEndDate: checkOptionalType(data.enrollmentEndDate, isDateYMD),
    startDate: checkType(data.startDate, isDateYMD),
    endDate: checkOptionalType(data.endDate, isDateYMD),
    owner: checkMember(data.owner),
    members: checkType(data.members, isArray).map(member => checkMember(member)),
    tags: checkType(data.tags, isArray).map(tag => checkTag(tag)),
  };
};
