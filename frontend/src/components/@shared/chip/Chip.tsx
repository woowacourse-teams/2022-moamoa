import { css } from '@emotion/react';
import styled from '@emotion/styled';

export type ChipProps = {
  children: string;
  variant?: 'primary' | 'secondary';
};

const Chip: React.FC<ChipProps> = ({ children, variant = 'primary' }) => {
  return <Self variant={variant}>{children}</Self>;
};

export default Chip;

type StyledChipProps = Required<Pick<ChipProps, 'variant'>>;

export const Self = styled.span<StyledChipProps>`
  ${({ theme, variant }) => css`
    display: inline-block;

    min-width: 80px;
    padding: 8px 10px;

    font-size: ${theme.fontSize.sm};
    text-align: center;
    border-radius: ${theme.radius.md};
    color: ${theme.colors.white};
    background-color: ${theme.colors.primary.base};

    ${variant === 'secondary' &&
    css`
      color: ${theme.colors.black};
      background-color: ${theme.colors.secondary.base};
    `}
  `}
`;
