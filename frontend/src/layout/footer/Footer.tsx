import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { CssLength } from '@custom-types';

export type FooterProps = {
  children?: React.ReactNode;
  marginBottom?: CssLength;
};

const Footer: React.FC<FooterProps> = ({ children = '그린론 디우 베루스 병민 짱구 태태', marginBottom = 0 }) => {
  return <Self marginBottom={marginBottom}>{children}</Self>;
};

type StyledFooterProps = Required<Pick<FooterProps, 'marginBottom'>>;

export const Self = styled.footer<StyledFooterProps>`
  ${({ theme, marginBottom }) => css`
    padding: 24px 0;
    margin-bottom: ${marginBottom};

    text-align: center;
    color: ${theme.colors.secondary.dark};
    border-top: 1px solid ${theme.colors.secondary.dark};
  `}
`;

export default Footer;
