import { useEffect, useMemo, useRef } from 'react';

import type { Noop } from '@custom-types';

type InfiniteScrollProps = {
  onContentLoad: Noop;
  observingCondition: boolean;
  children: React.ReactNode;
};

const InfiniteScroll: React.FC<InfiniteScrollProps> = ({
  onContentLoad: handleContentLoad,
  observingCondition,
  children,
}) => {
  const endRef = useRef<HTMLDivElement>(null);

  const endOfContentObserver = useMemo(
    () =>
      new IntersectionObserver(entries => {
        if (entries[0].isIntersecting) {
          handleContentLoad();
        }
      }),
    [handleContentLoad],
  );

  useEffect(() => {
    if (!endRef.current) return;
    if (observingCondition) {
      endOfContentObserver.observe(endRef.current);
      return;
    }
    endOfContentObserver.unobserve(endRef.current);
  }, [observingCondition, endRef.current]);

  return (
    <>
      {children}
      <div ref={endRef} />
    </>
  );
};

export default InfiniteScroll;
