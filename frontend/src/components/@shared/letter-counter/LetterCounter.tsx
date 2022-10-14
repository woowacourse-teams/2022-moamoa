import { css } from '@emotion/react';
import styled from '@emotion/styled';

export type LetterCounterProps = {
  count: number;
  maxCount: number;
};

const LetterCounter: React.FC<LetterCounterProps> = ({ count, maxCount }) => {
  return (
    <Self>
      <span>{Math.min(count, maxCount)}</span>/<span>{maxCount}</span>
    </Self>
  );
};

const Self = styled.div`
  ${({ theme }) => css`
    font-size: ${theme.fontSize.sm};
    color: ${theme.colors.secondary.dark};
    & > span {
      color: ${theme.colors.secondary.dark};
    }
    & > span:first-of-type {
      color: ${theme.colors.black};
    }
  `}
`;

export default LetterCounter;
