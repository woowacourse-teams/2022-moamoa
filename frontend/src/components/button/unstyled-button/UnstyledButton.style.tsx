import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { UnstyledButtonProps } from '@components/button/unstyled-button/UnstyledButton';

export type StyledUnstyledButtonProps = Required<Pick<UnstyledButtonProps, 'fontSize'>>;

export const UnstyledButton = styled.button<StyledUnstyledButtonProps>`
  ${({ theme, fontSize }) => css`
    font-size: ${theme.fontSize[fontSize]};

    border: none;
    outline: none;
    background-color: transparent;
  `}
`;
