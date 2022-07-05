import styled from '@emotion/styled';

import * as Logo from '@components/Logo/style';
import * as SearchBar from '@components/SearchBar/style';

export const Row = styled.header`
  ${({ theme }) => `
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 20px;
  
    padding: 20px 40px;

    border-bottom: 1px solid ${theme.colors.secondary.dark};
  
    @media (max-width: 1100px) {
      ${Logo.ImageContainer} {
        margin-right: 0;
      }
      ${Logo.BorderText} {
        display: none;
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
