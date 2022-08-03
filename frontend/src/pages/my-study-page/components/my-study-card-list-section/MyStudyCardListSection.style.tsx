import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const SectionTitle = styled.h3`
  margin-bottom: 20px;

  font-size: 24px;
  font-weight: 700;
`;

export const MyStudyList = styled.ul`
  display: grid;
  grid-template-columns: repeat(3, minmax(auto, 1fr));
  grid-template-rows: 1fr;
  gap: 20px;

  & > li {
    width: 100%;
  }

  ${mqDown('lg')} {
    grid-template-columns: repeat(2, minmax(auto, 1fr));
  }
  ${mqDown('sm')} {
    grid-template-columns: repeat(1, minmax(auto, 1fr));
  }
`;

export const MyStudyCardListSection = styled.section`
  padding: 8px;
`;
