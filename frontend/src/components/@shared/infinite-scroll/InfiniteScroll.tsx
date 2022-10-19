import { useEffect, useMemo, useRef } from 'react';

import type { Noop } from '@custom-types';

export type InfiniteScrollProps = {
  onContentLoad: Noop;
  isContentLoading: boolean;
  children: React.ReactNode;
};

const InfiniteScroll: React.FC<InfiniteScrollProps> = ({
  onContentLoad: handleContentLoad,
  isContentLoading,
  children,
}) => {
  const endOfContentRef = useRef<HTMLDivElement>(null);

  const endOfContentObserver = useMemo(
    () =>
      new IntersectionObserver(([entry]) => {
        if (entry.isIntersecting) {
          handleContentLoad();
        }
      }),
    [handleContentLoad],
  );

  useEffect(() => {
    if (!endOfContentRef.current) return;

    if (!isContentLoading) {
      // 콘텐트 로딩이 끝나면 타겟을 다시 observe -> 뷰포트 내부에 타겟이 존재하면 콘텐트 추가 로드
      endOfContentObserver.observe(endOfContentRef.current);
      return;
    }

    return () => endOfContentObserver.disconnect();
  }, [isContentLoading, endOfContentRef.current]);

  return (
    <>
      {children}
      <div ref={endOfContentRef} />
    </>
  );
};

export default InfiniteScroll;
