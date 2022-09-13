import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

import { SitePage } from '@custom-types';

export const dynamicHeight = (page?: SitePage) => {
  if (page === 'studyroom') {
    return css`
      height: calc(100vh - 80px);
    `;
  }
  return css``;
};

export const Main = styled.main<{ page?: SitePage }>`
  padding: 120px 0 80px;
  min-height: calc(100vh - 80px);
  height: auto;

  ${mqDown('md')} {
    padding-top: 90px;
  }

  ${mqDown('sm')} {
    padding-top: 80px;
  }
`;
