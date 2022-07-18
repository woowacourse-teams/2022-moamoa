import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const StudyFloatBox = styled.div`
  ${({ theme }) => css`
    padding: 40px;

    background: ${theme.colors.white};
    border: 3px solid ${theme.colors.primary.base};
    box-shadow: 8px 8px 0px ${theme.colors.secondary.dark};
    border-radius: 25px;
  `}
`;
