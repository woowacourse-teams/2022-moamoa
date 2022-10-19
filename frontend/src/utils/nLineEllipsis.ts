import { css } from '@emotion/react';

export const nLineEllipsis = (numOfLine: number) => css`
  display: -webkit-box;
  overflow: clip;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: ${numOfLine};

  word-break: break-all;
`;
