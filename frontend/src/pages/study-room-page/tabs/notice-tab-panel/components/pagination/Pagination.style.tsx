import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

export const Pagination = styled.div``;

const activeButtonStyle = (theme: Theme) => css`
  background-color: ${theme.colors.primary.light};
  color: ${theme.colors.white};
`;

type PaginationButtonProps = {
  active: boolean;
};

const buttonWidth = '32px';
const buttonHeight = '32px';

export const PaginationButton = styled.button<PaginationButtonProps>`
  ${({ theme, active }) => css`
    display: inline-flex;
    align-items: center;
    justify-content: center;

    background-color: transparent;
    outline: 0px;
    line-height: 1.5;
    border-radius: 4px;
    text-align: center;
    min-width: ${buttonWidth};
    height: ${buttonHeight};
    padding: 0px 6px;
    margin: 0px 3px;
    color: ${theme.colors.black};
    border: 1px solid ${theme.colors.secondary.dark};

    ${active && activeButtonStyle(theme)}
  `}
`;

export const Inner = styled.div`
  ${({ theme }) => css`
    display: flex;
    justify-content: center;

    ul,
    li {
      list-style: none;
      display: inline;
      padding-left: 0px;
    }
    ul {
      display: flex;
      row-gap: 10px;
    }
    li {
      min-width: ${buttonWidth};
      height: ${buttonHeight};
      text-align: center;
      vertical-align: middle;
    }
  `}
`;
