import { css } from '@emotion/react';

import * as S from '@layout/footer/Footer.style';

type FooterProps = {
  marginBottom: string;
};

const Footer: React.FC<FooterProps> = ({ marginBottom }) => {
  return (
    <S.Footer
      css={css`
        margin-bottom: ${marginBottom};
      `}
    >
      그린론 디우 베루스 병민 짱구 태태
    </S.Footer>
  );
};

export default Footer;
