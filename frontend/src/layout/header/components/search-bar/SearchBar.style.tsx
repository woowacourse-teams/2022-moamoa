import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const Container = styled.div`
  position: relative;
`;

export const Input = styled.input`
  ${({ theme }) => css`
    width: 100%;
    padding: 8px 40px;
    overflow: hidden;

    font-size: ${theme.fontSize.lg};
    border-radius: ${theme.radius.lg};
    border: 3px solid ${theme.colors.primary.base};

    text-align: center;

    &:focus {
      border-color: ${theme.colors.primary.light};
      & + button {
        svg {
          stroke: ${theme.colors.primary.light};
        }
      }
    }

    ${mqDown('md')} {
      font-size: ${theme.fontSize.lg};
    }
    ${mqDown('sm')} {
      font-size: ${theme.fontSize.md};
    }
  `}
`;
