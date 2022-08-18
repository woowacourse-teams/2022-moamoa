import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const FilterButtons = styled.ul`
  display: flex;
  justify-content: center;
  align-items: center;
  column-gap: 24px;

  ${mqDown('sm')} {
    column-gap: 16px;
  }
`;
