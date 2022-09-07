import { css } from '@emotion/react';
import styled from '@emotion/styled';

const twoLineEllipsis = css`
  overflow: clip;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  word-break: break-all;
`;

export const Container = styled.div`
  display: flex;
`;

export const Description = styled.p`
  margin-left: 8px;

  font-size: ${theme.fontSize.sm};

  ${twoLineEllipsis}
`;
