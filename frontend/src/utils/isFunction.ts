type TFunction = (...args: any[]) => any;

const isFunction = (val: unknown): val is TFunction => {
  return typeof val === 'function';
};

export default isFunction;
