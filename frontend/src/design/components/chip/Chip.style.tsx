import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { ChipProps } from '@design/components/chip/Chip';

type StyleChipProps = Required<Pick<ChipProps, 'variant'>>;

export const Chip = styled.span<StyleChipProps>`
  ${({ theme, variant }) => css`
    display: inline-block;

    min-width: 80px;
    padding: 8px 10px;

    font-size: ${theme.fontSize.sm};
    text-align: center;
    border-radius: ${theme.radius.md};
    color: ${variant === 'secondary' ? theme.colors.black : theme.colors.white};
    background-color: ${variant === 'secondary' ? theme.colors.secondary.base : theme.colors.primary.base};
  `}
`;
