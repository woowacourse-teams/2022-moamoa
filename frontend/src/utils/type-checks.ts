import isNullOrUndefined from '@utils/isNullOrUndefined';

const isObjectType = (value: unknown) => typeof value === 'object';

export const isObject = <T extends object>(value: unknown): value is T =>
  !isNullOrUndefined(value) && !Array.isArray(value) && isObjectType(value);

export const isString = (value: unknown): value is string => typeof value === 'string';
