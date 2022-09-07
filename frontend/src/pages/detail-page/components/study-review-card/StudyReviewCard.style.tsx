import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const StudyReviewCard = styled.div`
  ${({ theme }) => css`
    height: 100%;
    max-height: 150px;
    padding: 16px;

    border-radius: 15px;
    box-shadow: 0 0 2px 1px ${theme.colors.secondary.base};
  `}
`;

export const AuthorContainer = styled.div`
  display: flex;
  align-items: center;
  column-gap: 8px;

  margin-bottom: 12px;
`;

export const AuthorInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

export const AuthorName = styled.span`
  ${({ theme }) => css`
    margin-bottom: 4px;

    font-weight: ${theme.fontWeight.bold};
  `}
`;

export const ReviewDate = styled.span`
  ${({ theme }) => css`
    color: ${theme.colors.secondary.dark};
  `}
`;

export const Review = styled.p`
  display: -webkit-box;

  line-height: 24px;
  -webkit-line-clamp: 3;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
`;
