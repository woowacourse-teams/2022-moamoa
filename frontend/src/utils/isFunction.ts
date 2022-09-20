type TFunction = (...args: any[]) => any;

const isFunction = <T extends TFunction>(val: unknown): val is T => {
  return typeof val === 'function';
};

export default isFunction;
