import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { CssLength } from '@custom-types';

export type WrapperProps = {
  children: React.ReactNode;
  space?: CssLength;
};

const Wrapper: React.FC<WrapperProps> = ({ children, space = '20px' }) => {
  return <Self space={space}>{children}</Self>;
};

type StyledWrapperProps = Required<Pick<WrapperProps, 'space'>>;

const Self = styled.div<StyledWrapperProps>`
  ${({ space }) => css`
    width: 100%;
    max-width: 1280px;
    margin: 0 auto;
    padding: 0 ${space};
  `}
`;

export default Wrapper;
