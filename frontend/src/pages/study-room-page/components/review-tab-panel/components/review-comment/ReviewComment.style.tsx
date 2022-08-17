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

export const Content = styled.div`
  line-height: 20px;
`;

export const KebabMenuContainer = styled.div`
  position: relative;
`;

export const DropBoxButtonList = styled.ul`
  ${({ theme }) => css`
    display: flex;
    flex-direction: column;
    row-gap: 8px;
    padding: 8px;

    & > li:first-of-type {
      padding-bottom: 8px;
      border-bottom: 1px solid ${theme.colors.secondary.base};
    }
  `}
`;

export const DropBoxButton = styled.button`
  padding: 10px;

  background-color: transparent;
  border: none;
  white-space: nowrap;
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
