import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Name = styled.span`
  font-size: 20px;
  font-weight: 700;
`;

export const Author = styled.div`
  display: flex;
  column-gap: 10px;
  align-items: center;
`;

export const Date = styled.span`
  ${({ theme }) => css`
    font-size: 16px;
    color: ${theme.colors.secondary.dark};
  `}
`;

export const KebabMenu = styled.ul`
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

export const Content = styled.div`
  line-height: 20px;
`;

export const DropDownMenu = styled.ul`
  ${({ theme }) => css`
    position: absolute;
    top: calc(100% + 3px);
    right: 6px;
    z-index: 3;

    display: flex;
    flex-direction: column;
    row-gap: 8px;

    font-size: 24px;
    padding: 10px;

    background-color: ${theme.colors.white};
    border: 1px solid ${theme.colors.secondary.dark};
    border-radius: 5px;

    & > li:first-of-type {
      padding-bottom: 8px;
      border-bottom: 1px solid ${theme.colors.secondary.base};
    }

    button {
      padding: 10px;

      background-color: transparent;
      border: none;
      white-space: nowrap;
    }
  `}
`;

export const DropDown = styled.div<DropDownProps>`
  ${({ isOpen }) => css`
    position: relative;

    ${DropDownMenu} {
      display: ${isOpen ? 'flex' : 'none'};
    }
  `}
`;

export const ReviewComment = styled.div`
  ${({ theme }) => css`
    display: flex;
    flex-direction: column;
    row-gap: 15px;

    & > .top {
      display: flex;
      justify-content: space-between;
      align-items: center;
      & > .left {
        .user-info {
          display: flex;
          align-items: center;
          column-gap: 6px;
          .username {
            font-weight: 900;
          }
          .date {
            font-size: 12px;
            color: ${theme.colors.secondary.dark};
          }
          .right {
            display: flex;
            flex-direction: column;
            row-gap: 2px;
          }
        }
      }
      & > .right {
        display: flex;
        column-gap: 20px;
      }
    }
  `}
`;
