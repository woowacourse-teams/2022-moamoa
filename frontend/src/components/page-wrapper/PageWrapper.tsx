import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type CssLength } from '@custom-types';

export type PageWrapperProps = {
  children: React.ReactNode;
  space?: CssLength;
};

const PageWrapper: React.FC<PageWrapperProps> = ({ children, space = '20px' }) => {
  return <Self space={space}>{children}</Self>;
};

type StyledPageWrapperProps = Required<Pick<PageWrapperProps, 'space'>>;

const Self = styled.div<StyledPageWrapperProps>`
  ${({ space }) => css`
    width: 100%;
    max-width: 1280px;
    margin: 0 auto;
    padding: 0 ${space};
  `}
`;

export default PageWrapper;
