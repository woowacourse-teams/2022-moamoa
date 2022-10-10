import type { CategoryName, DateYMD, RecruitmentStatus, StudyStatus, UserRole } from '@custom-types';

export const isNull = (value: unknown): value is null => value === null;

export const isNullOrUndefined = (value: unknown): value is null | undefined => value === undefined || value === null;

export const isBoolean = (value: unknown): value is boolean => typeof value === 'boolean';

export const isString = (value: unknown): value is string => typeof value === 'string';

export const isNumber = (value: unknown): value is number => typeof value === 'number';

export const isFunction = (val: unknown): val is (...args: any) => any => typeof val === 'function';

export const isObject = <T extends object>(value: unknown): value is T =>
  typeof value === 'object' && !isNullOrUndefined(value) && !Array.isArray(value);

export const isArray = (value: unknown): value is Array<unknown> => Array.isArray(value);

export const isDateYMD = (value: unknown): value is DateYMD => {
  if (!isString(value)) return false;
  const regex = /\d{4}-\d{2}-\d{2}/;
  return regex.test(value);
};

export const isUserRole = (value: unknown): value is UserRole =>
  isString(value) && ['OWNER', 'MEMBER', 'NON_MEMBER'].includes(value);

export const isStudyStatus = (value: unknown): value is StudyStatus =>
  isString(value) && ['PREPARE', 'IN_PROGRESS', 'DONE'].includes(value);

export const isRecruitmentStatus = (value: unknown): value is RecruitmentStatus =>
  isString(value) && ['RECRUITMENT_START', 'RECRUITMENT_END'].includes(value);

export const isCategoryName = (value: unknown): value is CategoryName =>
  isString(value) && ['generation', 'area', 'subject'].includes(value);

export const hasOwnProperty = <X extends object, Y extends PropertyKey>(
  obj: X,
  prop: Y,
): obj is X & Record<Y, unknown> => obj.hasOwnProperty(prop);

export const hasOwnProperties = <X extends object, Y extends PropertyKey>(
  obj: X,
  props: Array<Y>,
): obj is X & Record<Y, unknown> => props.every(prop => obj.hasOwnProperty(prop));

type isTypeFn<T> = (value: unknown) => value is T;

function checkType<T>(value: unknown, isType: isTypeFn<T>, isOptional: true): T | undefined;
function checkType<T>(value: unknown, isType: isTypeFn<T>, isOptional?: false): T;
function checkType<T>(value: unknown, isType: isTypeFn<T>, isOptional?: boolean): T | undefined {
  if (isOptional) {
    if (!isType(value) && !isNullOrUndefined(value)) {
      console.error(`${isType} ${value} does not have correct type`);
      throw new Error(`${value} does not have correct type`);
    }

    return value ?? undefined;
  }

  if (!isType(value)) {
    console.error(`${isType} ${value} does not have correct type`);
    throw new Error(`${value} does not have correct type`);
  }

  return value;
}

export default checkType;
