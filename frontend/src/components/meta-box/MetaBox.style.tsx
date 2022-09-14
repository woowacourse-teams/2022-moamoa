import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const MetaBox = styled.div`
  ${({ theme }) => css`
    background-color: ${theme.colors.white};
    min-width: 255px;
    border: 1px solid ${theme.colors.secondary.base};
    border-radius: ${theme.radius.sm};
  `}
`;

export const Title = styled.h2`
  ${({ theme }) => css`
    padding: 8px 12px;

    font-size: ${theme.fontSize.md};
    font-weight: ${theme.fontWeight.bold};

    border-bottom: 1px solid ${theme.colors.secondary.base};
  `}
`;

export const Content = styled.div`
  padding: 8px 12px;
`;
