import { css } from '@emotion/react';
import styled from '@emotion/styled';

export {
  UserInfo,
  AvatarLink,
  Username,
  UsernameContainer,
  UsernameLink,
} from '@study-room-page/components/review-tab-panel/components/review-edit-form/ReviewEditForm.style';

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

export const ReviewCommentHead = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const ReviewCommentBody = styled.div``;

export const ReviewCommentFooter = styled.div``;

export const ReviewComment = styled.div`
  display: flex;
  flex-direction: column;
  row-gap: 15px;
`;
