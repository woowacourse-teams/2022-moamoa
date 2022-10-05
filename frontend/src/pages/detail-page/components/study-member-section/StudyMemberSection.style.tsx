import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const MemberList = styled.ul`
  display: grid;
  grid-template-columns: repeat(2, minmax(auto, 1fr));
  grid-column-gap: 30px;
  grid-row-gap: 20px;

  ${mqDown('md')} {
    display: flex;
    flex-direction: column;
    row-gap: 20px;
  }
`;
