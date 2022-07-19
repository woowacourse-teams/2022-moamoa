import { SerializedStyles, css } from '@emotion/react';
import styled from '@emotion/styled';

import type { AvatarProps } from './Avatar';

const dynamicSize = {
  sm: css`
    width: 36px;
    min-width: 36px;
    height: 36px;
  `,
  md: css`
    width: 42px;
    min-width: 42px;
    height: 42px;
  `,
  lg: css`
    width: 65px;
    min-width: 65px;
    height: 65px;
  `,
};

type DynamicImageContainerFn = (props: Pick<AvatarProps, 'size'>) => SerializedStyles;

const dynamicImageContainer: DynamicImageContainerFn = props => css`
  ${dynamicSize[props.size]}
`;

export const Avatar = styled.div`
  ${({ theme }) => css`
    width: 36px;
    min-width: 36px;
    height: 36px;
    border-radius: 50%;
    box-shadow: 0px 1px 5px 0px ${theme.colors.secondary.dark};
    overflow: hidden;
    transition: opacity 0.2s ease;

    &:hover,
    &:active {
      opacity: 0.9;
    }
  `}

  ${dynamicImageContainer}
`;

export const Image = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
`;
