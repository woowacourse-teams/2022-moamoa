import type { CssLength } from '@custom-types';

import * as S from '@layout/footer/Footer.style';

export type FooterProps = {
  children?: React.ReactNode;
  marginBottom?: CssLength;
};

const Footer: React.FC<FooterProps> = ({ children = '그린론 디우 베루스 병민 짱구 태태', marginBottom = 0 }) => {
  return <S.Footer marginBottom={marginBottom}>{children}</S.Footer>;
};

export default Footer;
