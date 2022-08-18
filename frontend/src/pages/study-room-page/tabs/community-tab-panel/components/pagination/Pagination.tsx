import React, { ReactNode, useState } from 'react';

import * as S from '@study-room-page/tabs/community-tab-panel/components/pagination/Pagination.style';

type PaginationProps = {
  count: number; // 전체 페이지 개수
  defaultPage: number; // 현재 페이지
  renderItem: (item: any) => ReactNode;
  onNumberButtonClick: (num: number) => void;
};

const range = (start: number, end: number): Array<number> => {
  const length = end - start + 1;
  return Array.from({ length }, (_, i) => start + i);
};

const Pagination: React.FC<PaginationProps> = ({ count, defaultPage, renderItem, onNumberButtonClick }) => {
  const [page, setPage] = useState<number>(defaultPage);

  const handleNumButtonClick = (num: number) => (e: React.MouseEvent<HTMLButtonElement>) => {
    setPage(num);
    onNumberButtonClick(num);
  };

  const renderItems = () => {
    let start: Array<number | '...'> = [];
    let middle: Array<number | '...'> = [];
    let end: Array<number | '...'> = [];

    if (count < 7) {
      start = range(1, count);
    } else if (page < 5) {
      start = range(1, 5);
      end = ['...', count];
    } else if (page > count - 4) {
      start = [1, '...'];
      end = range(count - 4, count);
    } else {
      start = [1, '...'];
      middle = [page - 1, page, page + 1];
      end = ['...', count];
    }

    const list = [...start, ...middle, ...end];

    return (
      <>
        {list.map((num, i) => {
          return (
            <li key={i}>
              {num === '...' ? (
                num
              ) : (
                <S.PaginationButton active={num === page} onClick={handleNumButtonClick(num)}>
                  {num}
                </S.PaginationButton>
              )}
            </li>
          );
        })}
      </>
    );
  };

  return (
    <S.Pagination>
      <S.Inner>
        <ul>{renderItems()}</ul>
      </S.Inner>
    </S.Pagination>
  );
};

export default Pagination;
