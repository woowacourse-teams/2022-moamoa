import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { WrapperProps } from './Wrapper';

type StyledWrapperProps = Required<Pick<WrapperProps, 'space'>>;

export const Wrapper = styled.div<StyledWrapperProps>`
  ${({ space }) => css`
    width: 100%;
    max-width: 1280px;
    margin: 0 auto;
    padding: 0 ${space};
  `}
`;
