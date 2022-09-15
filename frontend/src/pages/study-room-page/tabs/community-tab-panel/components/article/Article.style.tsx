import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { Button as OriginalButton } from '@community-tab/components/button/Button.style';

export const Header = styled.div`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;

    padding-bottom: 20px;

    border-bottom: 1px solid ${theme.colors.secondary.base};
  `}
`;

export const Main = styled.div``;

export const Title = styled.h1`
  ${({ theme }) => css`
    font-size: ${theme.fontSize.xxl};
    padding-top: 20px;
    padding-bottom: 20px;
  `}
`;

export const Content = styled.div`
  min-height: 400px;
  padding-bottom: 20px;
`;

export const Footer = styled.div`
  ${({ theme }) => css`
    display: flex;
    align-items: center;

    column-gap: 20px;
    color: ${theme.colors.secondary.dark};
  `}
`;

export const Author = styled.div`
  display: flex;
  align-items: center;

  column-gap: 10px;
`;

export const CreatedAt = styled.span``;

export const Button = styled(OriginalButton)``;
