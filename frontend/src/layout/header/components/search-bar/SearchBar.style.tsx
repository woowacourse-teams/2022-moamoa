import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
  position: relative;
`;

export const Input = styled.input`
  ${({ theme }) => css`
    width: 100%;
    padding: 8px 40px;
    overflow: hidden;

    font-size: 20px;
    border-radius: 20px;
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
  `}
`;

export const Button = styled.button`
  ${({ theme }) => css`
    display: flex;
    align-items: center;

    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;

    padding-right: 20px;
    font-size: 20px;

    background: transparent;
    border: none;

    &:hover,
    &:active {
      svg {
        stroke: ${theme.colors.primary.light};
      }
    }
  `}
`;
