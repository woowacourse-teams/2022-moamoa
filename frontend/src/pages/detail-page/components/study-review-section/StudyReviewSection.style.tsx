import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const ReviewSection = styled.section`
  ${({ theme }) => css`
    padding: 16px;

    border-radius: ${theme.radius.md};
  `}
`;

export const ReviewTitle = styled.h3`
  ${({ theme }) => css`
    margin-bottom: 30px;

    font-size: ${theme.fontSize.xl};
    font-weight: ${theme.fontWeight.bold};

    & > span {
      font-size: ${theme.fontSize.md};
    }
  `}
`;

export const ReviewList = styled.ul`
  display: flex;
  flex-wrap: wrap;
  gap: 16px 60px;

  ${mqDown('md')} {
    flex-direction: column;
    row-gap: 30px;
  }
`;

export const ReviewListItem = styled.li`
  width: calc(50% - 30px);

  ${mqDown('md')} {
    width: 100%;
  }
`;

export const MoreButtonContainer = styled.div`
  padding: 16px 0;

  text-align: right;
`;
