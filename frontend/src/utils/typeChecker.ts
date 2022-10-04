export const isNullOrUndefined = (value: unknown): value is null | undefined => value === undefined || value === null;

export const isString = (value: unknown): value is string => typeof value === 'string';

export const isNumber = (value: unknown): value is number => typeof value === 'number';

export const isFunction = (val: unknown): val is (...args: any) => any => typeof val === 'function';

const isObjectType = (value: unknown) => typeof value === 'object';

export const isObject = <T extends object>(value: unknown): value is T =>
  !isNullOrUndefined(value) && !Array.isArray(value) && isObjectType(value);
