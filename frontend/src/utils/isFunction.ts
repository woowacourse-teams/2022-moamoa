type TFunction = (...args: any[]) => any;

const isFunction = <T extends TFunction>(val: unknown): val is T => {
  return Object.prototype.toString.call(val) === '[object Function]';
};

export default isFunction;
