import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

import type { ButtonProp } from '@components/button/Button';

const applyOutlineButtonStyle = ({ theme, isLoading }: { theme: Theme; isLoading?: boolean }) => css`
  transition: 0.3s;
  background-color: transparent;
  border: 1px solid ${theme.colors.primary.base};
  color: ${isLoading ? 'transparent' : theme.colors.primary.base};

  &:hover {
    background-color: ${theme.colors.primary.base};
    border: 1px solid transparent;
    color: white;
  }
`;

export const LoadingIndicator = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  .spinning-loader {
    width: 20px;
    height: 20px;
    border: 5px solid rgba(29, 161, 242, 0.2);
    border-radius: 50%;
    background: transparent;
    animation-name: rotate-s-loader;
    animation-iteration-count: infinite;
    animation-duration: 1s;
    animation-timing-function: linear;
    position: relative;
    .dot {
      width: 5px;
      height: 5px;
      background-color: rgb(29, 161, 242);
      border-radius: 50%;
      position: absolute;
      left: -3px;
    }

    @keyframes rotate-s-loader {
      from {
        transform: rotate(0);
      }
      to {
        transform: rotate(360deg);
      }
    }
  }
`;

export const Button = styled.button<ButtonProp>`
  ${({ theme, fluid, outline, isLoading }) => css`
    width: ${fluid ? '100%' : 'auto'};
    padding: 20px 10px;

    text-align: center;
    font-size: 24px;
    border: none;
    border-radius: 10px;
    background-color: ${theme.colors.primary.base};
    color: ${isLoading ? theme.colors.primary.base : 'white'};

    white-space: nowrap;

    ${outline && applyOutlineButtonStyle({ theme, isLoading })}
  `}
`;

export const ButtonContainer = styled.div`
  position: relative;
  height: 100%;

  ${LoadingIndicator} {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
  }

  ${Button} {
    min-height: 30px;
  }
`;
