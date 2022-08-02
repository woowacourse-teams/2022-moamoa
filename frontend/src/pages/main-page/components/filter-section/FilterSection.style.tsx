import { mqDown } from '@utils';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const FilterSectionContainer = styled.div`
  ${({ theme }) => css`
    position: sticky;
    top: 85px;
    max-width: 1280px;
    margin: 0 auto 16px;
    padding: 16px 20px 0;

    background-color: ${theme.colors.secondary.light};
    border-bottom: 1px solid ${theme.colors.secondary.dark};
    z-index: 1;
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
    height: 100%;
    padding: auto 0;

    background-color: ${theme.colors.secondary.light}cc;
    z-index: 2;

    ${mqDown('md')} {
      display: none;
    }
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

export const VerticalLine = styled.div`
  ${({ theme }) => css`
    height: 40px;
    border-right: 1px solid ${theme.colors.secondary.dark};
  `}

  ${mqDown('sm')} {
    display: none;
  }
`;
