import { css } from '@emotion/react';
import styled from '@emotion/styled';

const onelineEllipsis = css`
  overflow: clip;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  word-break: break-all;
`;

export const PreviewContainer = styled.div`
  position: relative;

  &:hover {
    & > div {
      visibility: visible;
      opacity: 1;
    }
  }
`;

export const PreviewImageContainer = styled.div`
  ${({ theme }) => css`
    height: 140px;
    margin-bottom: 8px;
    overflow: hidden;

    border-radius: ${theme.radius.md};
  `}
`;

export const PreviewDomain = styled.div`
  ${({ theme }) => css`
    position: absolute;
    bottom: 62px;
    right: 8px;
    width: 110px;
    height: 30px;
    padding: 4px;

    background-color: ${theme.colors.white};
    border-radius: ${theme.radius.md};
    text-align: center;
    visibility: hidden;
    opacity: 0;
    transition: visibility 0.2s ease, opacity 0.2s ease;

    & > span {
      font-weight: ${theme.fontWeight.bold};
      font-size: ${theme.fontSize.sm};
    }
  `}

  ${onelineEllipsis}
`;

export const PreviewContentContainer = styled.div`
  padding: 0 8px;
`;

export const PreviewTitle = styled.p`
  ${({ theme }) => css`
    margin-bottom: 8px;

    font-weight: ${theme.fontWeight.bold};
  `}

  ${onelineEllipsis}
`;

export const PreviewDescription = styled.p`
  ${({ theme }) => css`
    margin-bottom: 8px;

    font-size: ${theme.fontSize.sm};
    color: ${theme.colors.secondary.dark};
  `}

  ${onelineEllipsis}
`;
