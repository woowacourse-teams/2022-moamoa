import { useEffect, useRef, useState } from 'react';

export const useThrottledValue = <T>(originalValue: T, delay = 500) => {
  const [throttledValue, setThrottledValue] = useState<T>(originalValue);
  const throttle = useRef<null | NodeJS.Timeout>(null);

  useEffect(() => {
    if (throttle.current) return;
    throttle.current = setTimeout(() => {
      setThrottledValue(originalValue);
      throttle.current = null;
    }, delay);
  }, [originalValue]);

  return [throttledValue];
};
