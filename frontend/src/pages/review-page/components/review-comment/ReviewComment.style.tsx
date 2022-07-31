import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Name = styled.span`
  font-size: 20px;
  font-weight: 700;
`;

export const Author = styled.div`
  display: flex;
  column-gap: 6px;
  align-items: center;
`;

export const Date = styled.span`
  ${({ theme }) => css`
    font-size: 16px;
    color: ${theme.colors.secondary.dark};
  `}
`;

export const DotDotDot = styled.ul`
  ${({ theme }) => css`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    column-gap: 2px;
    .li {
      width: 2px;
      height: 2px;
      background-color: ${theme.colors.black};
    }
  `}
`;

type DropDownProps = {
  isOpen?: boolean;
};

export const DropDown = styled.div<DropDownProps>`
  ${({ theme, isOpen }) => css`
    position: relative;
    .menu {
      position: absolute;
      top: 100%;
      right: 100%;

      display: ${isOpen ? 'flex' : 'none'};
      flex-direction: column;
      row-gap: 15px;

      font-size: 24px;
      padding: 10px;

      background-color: ${theme.colors.white};
      border: 1px solid ${theme.colors.secondary.dark};

      button {
        padding: 10px;

        background-color: transparent;
        border: none;
        white-space: nowrap;
      }
    }
  `}
`;

export const ReviewComment = styled.div`
  display: flex;
  flex-direction: column;
  row-gap: 15px;

  & > .top {
    position: relative;

    display: flex;
    justify-content: space-between;
    align-items: center;

    & > .right {
      display: flex;
      column-gap: 20px;
    }
  }
`;
