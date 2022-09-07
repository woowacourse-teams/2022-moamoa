import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const PageTitle = styled.h2`
  ${({ theme }) => css`
    font-size: ${theme.fontSize.xxl};
    text-align: center;
  `}
`;

export const SectionContainer = styled.div`
  margin-bottom: 20px;
`;
