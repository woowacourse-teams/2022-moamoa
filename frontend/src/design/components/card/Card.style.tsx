import { css } from '@emotion/react';
import styled from '@emotion/styled';

import type { MakeRequired } from '@custom-types';

import { type CardProps } from '@design/components/card/Card';

type StyleCardProps = MakeRequired<Pick<CardProps, 'height' | 'width'>, 'width'>;

export const Card = styled.div<StyleCardProps>`
  ${({ width, height }) => css`
    display: flex;
    flex-direction: column;

    width: ${width};
    height: ${height};
    overflow: hidden;

    background-color: transparent;
  `}
`;
