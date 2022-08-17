import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const SectionTitle = styled.h3`
  margin-bottom: 20px;

  font-size: 24px;
  font-weight: 700;
`;

export const MyStudyCardListSection = styled.section`
  padding: 8px;
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

export const MyStudyCardItem = styled.li`
  position: relative;
`;

export const TrashButton = styled.button`
  ${({ theme }) => css`
    position: absolute;
    bottom: 12px;
    right: 12px;

    background-color: transparent;
    border: none;
    outline: none;

    & > svg {
      stroke: ${theme.colors.primary.base};

      &:hover,
      &:active {
        stroke: ${theme.colors.primary.light};
      }
    }
  `};
`;
