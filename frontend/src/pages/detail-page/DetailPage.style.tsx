import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const FloatButtonContainer = styled.div`
  min-width: 30%;

  ${mqDown('lg')} {
    display: none;
  }
`;

export const FixedBottomContainer = styled.div`
  display: none;

  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 5;

  ${mqDown('lg')} {
    display: block;
  }
`;
