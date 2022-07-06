import { css } from '@emotion/react';
import styled from '@emotion/styled';

import * as Logo from '@components/Logo/style';
import * as SearchBar from '@components/SearchBar/style';

export const SearchBarContainer = styled.div`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  width: 100%;
  max-width: 400px;
`;

export const Row = styled.header`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 20px;

    position: relative;

    padding: 20px 40px;

    background-color: ${theme.colors.secondary.light};
    border-bottom: 1px solid ${theme.colors.secondary.dark};

    @media (max-width: 1100px) {
      ${Logo.ImageContainer} {
        margin-right: 0;
      }
      ${Logo.BorderText} {
        display: none;
      }
      ${SearchBarContainer} {
        position: static;
        left: 0;
        top: 0;
        transform: none;
      }
    }

    @media (max-width: 800px) {
      padding: 16px 24px;
      ${SearchBar.Input} {
        font-size: 18px;
      }
    }

    @media (max-width: 500px) {
      padding: 10px 12px;
      ${SearchBar.Input} {
        font-size: 16px;
      }
    }
  `}
`;
