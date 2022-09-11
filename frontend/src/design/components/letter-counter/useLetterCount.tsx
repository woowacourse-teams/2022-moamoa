import { useState } from 'react';

const useLetterCount = (maxCount: number, initialCount = 0) => {
  const [count, setCount] = useState<number>(initialCount);
  return { count, setCount, maxCount };
};

export default useLetterCount;
