import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { FooterProps } from '@layout/footer/Footer';

type StyledFooterProps = Required<Pick<FooterProps, 'marginBottom'>>;

export const Footer = styled.footer<StyledFooterProps>`
  ${({ theme, marginBottom }) => css`
    padding: 24px 0;
    margin-bottom: ${marginBottom};

    text-align: center;
    color: ${theme.colors.secondary.dark};
    border-top: 1px solid ${theme.colors.secondary.dark};
  `}
`;
