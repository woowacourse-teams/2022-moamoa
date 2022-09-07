import { css } from '@emotion/react';
import type { Theme } from '@emotion/react';
import styled from '@emotion/styled';

import { MyStudyCardProps } from '@my-study-page/components/my-study-card/MyStudyCard';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
`;

export const Top = styled.div``;

export const Bottom = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const Title = styled.h4`
  ${({ theme }) => css`
    display: -webkit-box;
    overflow: clip;
    text-overflow: ellipsis;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;
    word-break: break-all;

    margin-bottom: 12px;

    font-size: ${theme.fontSize.lg};
    font-weight: ${theme.fontWeight.bold};
  `}
`;

export const Owner = styled.p`
  display: flex;
  align-items: center;
  column-gap: 2px;

  margin-bottom: 12px;

  & > svg {
    position: relative;
    top: -2px;
  }
`;

export const Tags = styled.ul`
  margin-bottom: 12px;
  display: flex;
  column-gap: 10px;
  flex-wrap: wrap;
`;

export const Period = styled.p``;

const disabledStyle = (theme: Theme) => css`
  border: 3px solid ${theme.colors.secondary.dark};
  box-shadow: 4px 4px 0 0 ${theme.colors.secondary.base};

  & * {
    color: ${theme.colors.secondary.dark} !important;
  }
  svg {
    stroke: ${theme.colors.secondary.dark} !important;
  }
`;

export const MyStudyCard = styled.div<Pick<MyStudyCardProps, 'disabled'>>`
  ${({ theme, disabled }) => css`
    position: relative;
    padding: 12px;
    overflow: hidden;

    height: 100%;

    border: 3px solid ${theme.colors.primary.base};
    border-radius: ${theme.radius.md};
    box-shadow: 4px 4px 0 0 ${theme.colors.secondary.dark};

    ${disabled && disabledStyle(theme)}
  `}
`;
