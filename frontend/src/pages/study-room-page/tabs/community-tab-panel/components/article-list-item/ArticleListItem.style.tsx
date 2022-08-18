import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const ArticleListItem = styled.li`
  ${({ theme }) => css`
    display: flex;
    align-items: center;

    border-bottom: 1px solid ${theme.colors.secondary.base};
    padding-bottom: 10px;
  `}
`;

export const Main = styled.div`
  flex: 1;
`;

export const Title = styled.h3`
  ${({ theme }) => css``}
`;

export const Content = styled.div``;

export const MoreInfo = styled.div`
  display: flex;
  align-items: center;
  column-gap: 20px;

  text-align: center;
  font-size: 13px;
  line-height: 20px;
`;

export const Author = styled.div`
  display: flex;
  align-items: center;
  column-gap: 8px;
`;

export const Username = styled.span`
  white-space: normal;
`;

export const Date = styled.div`
  display: flex;
  flex-direction: column;
`;
