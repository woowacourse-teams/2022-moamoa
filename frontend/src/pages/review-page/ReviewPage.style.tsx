import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const ReviewList = styled.ul`
  ${({ theme }) => css`
    display: flex;
    flex-direction: column;
    row-gap: 30px;

    & > li {
      padding-bottom: 30px;
      border-bottom: 1px solid ${theme.colors.secondary.base};

      &:last-of-type {
        border-bottom: none;
        padding-bottom: 0;
      }
    }
  `}
`;

export const ReviewPage = styled.div`
  display: flex;
  flex-direction: column;
  row-gap: 30px;
`;
