import { useState } from 'react';

import tw from '@utils/tw';

import { TextButton } from '@design/components/button';
import ButtonGroup from '@design/components/button-group/ButtonGroup';
import { MeatballMenuIcon } from '@design/icons';

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
            <li key={i} css={tw`p-8`}>
              {num === '...' ? (
                <MeatballMenuIcon />
              ) : (
                <TextButton
                  variant={num === page ? 'primary' : 'secondary'}
                  fontSize={num === page ? 'xl' : 'md'}
                  onClick={handleNumButtonClick(num)}
                >
                  {num}
                </TextButton>
              )}
            </li>
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

export default Pagination;
