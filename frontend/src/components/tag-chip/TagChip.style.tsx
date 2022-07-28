import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const TagChip = styled.div`
  ${({ theme }) => css`
    display: inline-block;

    min-width: 92px;
    padding: 6px 8px;

    font-size: 16px;
    line-height: 18px;
    text-align: center;
    color: ${theme.colors.primary.base};

    border-radius: 25px;
    border: 2px solid ${theme.colors.primary.base};
    background-color: ${theme.colors.white};
  `}
`;
