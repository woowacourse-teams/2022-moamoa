import { css } from '@emotion/react';

export const nLineEllipsis = (numOfLine: number) => css`
  overflow: clip;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: ${numOfLine};
  word-break: break-all;
`;
