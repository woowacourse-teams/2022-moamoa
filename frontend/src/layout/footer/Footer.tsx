import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type CustomCSS } from '@styles/custom-css';

export type FooterProps = {
  children?: React.ReactNode;
  custom?: CustomCSS<'marginBottom'>;
};

const Footer: React.FC<FooterProps> = ({ children = '그린론 디우 베루스 병민 짱구 태태', custom }) => {
  return <Self css={custom}>{children}</Self>;
};

export default Footer;

const Self = styled.footer`
  ${({ theme }) => css`
    padding: 24px 0;

    text-align: center;
    color: ${theme.colors.secondary.dark};
    border-top: 1px solid ${theme.colors.secondary.dark};
  `}
`;
