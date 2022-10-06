import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const FilterSectionContainer = styled.div`
  ${({ theme }) => css`
    position: sticky;
    top: 85px;
    z-index: 1;

    max-width: 1280px;
    margin: 0 auto 16px;
    padding: 16px 20px 0;

    background-color: ${theme.colors.secondary.light};
    border-bottom: 1px solid ${theme.colors.secondary.dark};
  `}

  ${mqDown('md')} {
    top: 70px;
  }

  ${mqDown('sm')} {
    top: 60px;
    padding: 16px 5px 0;
  }
`;

export const RightButtonContainer = styled.div`
  ${({ theme }) => css`
    display: flex;
    align-items: center;
    justify-content: center;

    position: absolute;
    top: 0;
    right: 20px;
    z-index: 2;

    height: 100%;
    padding: auto 0;

    background-color: ${theme.colors.secondary.light}66;
  `}
`;

export const LeftButtonContainer = styled(RightButtonContainer)`
  right: calc(100% - 20px - 24px);
`;

export const FilterSection = styled.section`
  display: flex;
  align-items: center;
  column-gap: 32px;

  padding: 16px auto 4px;
  margin: 0 20px 0 12px;
  overflow-x: auto;
  scroll-behavior: smooth;

  &::-webkit-scrollbar {
    display: none;
  }

  ${mqDown('sm')} {
    column-gap: 16px;
  }
`;
