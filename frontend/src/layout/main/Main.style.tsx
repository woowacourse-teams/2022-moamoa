import styled from '@emotion/styled';

import { mqDown } from '@utils/index';

export const Main = styled.main`
  padding: 120px 0 80px;
  min-height: calc(100vh - 80px);

  ${mqDown('md')} {
    padding-top: 90px;
  }

  ${mqDown('sm')} {
    padding-top: 80px;
  }
`;
