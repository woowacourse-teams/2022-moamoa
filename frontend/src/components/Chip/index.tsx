import { css } from '@emotion/react';

export interface ChipProps {
  children: string;
  disabled: boolean;
}

const Chip: React.FC<ChipProps> = ({ children, disabled = false }) => {
  return (
    <span
      css={theme => css`
        display: inline-block;
        width: 3.75rem;
        padding: 0.5rem 0.8rem;

        text-align: center;
        border-radius: 1rem;
        color: ${disabled ? theme.colors.black : theme.colors.white};
        background-color: ${disabled ? theme.colors.secondary.base : theme.colors.primary.base};
      `}
    >
      {children}
    </span>
  );
};

export default Chip;
