import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const MetaBox = styled.div`
  ${({ theme }) => css`
    background-color: ${theme.colors.white};
    min-width: 255px;
    border: 1px solid ${theme.colors.secondary.dark};
    box-shadow: 0 1px 1px rgb(0 0 0 / 4%);
    border-radius: ${theme.radius.xs};
  `}
`;

export const Title = styled.h2`
  ${({ theme }) => css`
    font-size: ${theme.fontSize.md};
    padding: 8px 12px;
    margin: 0;
    line-height: 1.4;

    border-bottom: 1px solid ${theme.colors.secondary.dark};
  `}
`;

export const Content = styled.div`
  padding: 8px 12px;
`;
