import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const LinkList = styled.ul`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;

  ${mqDown('lg')} {
    grid-template-columns: repeat(2, 1fr);
  }

  ${mqDown('sm')} {
    grid-template-columns: repeat(1, 1fr);
  }
`;
