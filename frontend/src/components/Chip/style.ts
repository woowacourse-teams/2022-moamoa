import styled from '@emotion/styled';

import type { ChipProps } from '@components/Chip';

export const StyledChip = styled.span<ChipProps>`
  ${({ theme, disabled }) => `
    display: inline-block;

    width: 5.75rem;
    padding: 0.5rem 0.8rem;
    
    text-align: center;
    border-radius: 1rem;
    color: ${disabled ? theme.colors.black : theme.colors.white};
    background-color: ${disabled ? theme.colors.secondary.base : theme.colors.primary.base};
  `}
`;
