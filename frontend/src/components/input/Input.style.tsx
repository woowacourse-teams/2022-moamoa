import { css } from '@emotion/react';
import styled from '@emotion/styled';

const invalidInputStyle = () => css`
  border: 1px solid red !important;
`;

type InputProps = {
  isValid?: boolean;
};

export const Input = styled.input<InputProps>`
  ${({ theme, isValid }) => css`
    border-radius: ${theme.radius.xs};
    border: 1px solid ${theme.colors.secondary.base};
    background-color: ${theme.colors.white};
    padding: 4px 8px;

    &:focus {
      border: 1px solid ${theme.colors.primary.light};
      outline: 1px solid transparent;
    }

    ${isValid && invalidInputStyle()}
  `}
`;
