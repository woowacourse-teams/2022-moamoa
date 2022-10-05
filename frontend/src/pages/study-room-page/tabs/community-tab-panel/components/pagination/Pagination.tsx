import { useState } from 'react';

import { Theme, css, useTheme } from '@emotion/react';

import { TextButton } from '@components/button';
import ButtonGroup from '@components/button-group/ButtonGroup';
import { MeatballMenuIcon } from '@components/icons';

type PaginationProps = {
  count: number; // 전체 페이지 개수
  defaultPage: number; // 현재 페이지
  onNumberButtonClick: (num: number) => void;
};

const range = (start: number, end: number): Array<number> => {
  const length = end - start + 1;
  return Array.from({ length }, (_, i) => start + i);
};

const Pagination: React.FC<PaginationProps> = ({ count, defaultPage, onNumberButtonClick }) => {
  const theme = useTheme();
  const [page, setPage] = useState<number>(defaultPage);

  const handleNumButtonClick = (num: number) => () => {
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
            <div
              key={i}
              css={css`
                padding: 8px;
              `}
            >
              {num === '...' ? (
                <MeatballMenuIcon />
              ) : (
                <PageButton theme={theme} num={num} page={page} onClick={handleNumButtonClick} />
              )}
            </div>
          );
        })}
      </>
    );
  };

  return (
    <ButtonGroup justifyContent="center" alignItems="center" gap="8px">
      {renderItems()}
    </ButtonGroup>
  );
};

type PageButtonProps = {
  theme: Theme;
  num: number;
  page: number;
  onClick: (num: number) => () => void;
};
const PageButton: React.FC<PageButtonProps> = ({ theme, num, page, onClick: handleClick }) => (
  <TextButton
    variant={num === page ? 'primary' : 'secondary'}
    onClick={handleClick(num)}
    custom={{ fontSize: num === page ? theme.fontSize.xl : theme.fontSize.md }}
  >
    {num}
  </TextButton>
);

export default Pagination;
