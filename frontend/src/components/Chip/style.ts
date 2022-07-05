import styled from '@emotion/styled';

import type { ChipProps } from '@components/Chip';

export const StyledChip = styled.span<ChipProps>`
  ${({ theme, disabled }) => `
    display: inline-block;

    width: 92px;
    padding: 8px 12.8px;
    
    text-align: center;
    border-radius: 16px;
    color: ${disabled ? theme.colors.black : theme.colors.white};
    background-color: ${disabled ? theme.colors.secondary.base : theme.colors.primary.base};
  `}
`;
