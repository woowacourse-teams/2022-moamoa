import styled from '@emotion/styled';

import { mqDown } from '@utils/index';

export const CardList = styled.ul`
  display: grid;
  grid-template-columns: repeat(4, minmax(auto, 1fr));
  grid-template-rows: 1fr;
  gap: 32px;

  & > li {
    cursor: pointer;
    width: 100%;
  }

  ${mqDown('lg')} {
    grid-template-columns: repeat(3, 1fr);
  }
  ${mqDown('md')} {
    grid-template-columns: repeat(2, 1fr);
  }
  ${mqDown('sm')} {
    grid-template-columns: repeat(1, 256px);
    place-content: center;
  }
`;

export const Page = styled.div``;
