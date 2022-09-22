import { css } from '@emotion/react';
import styled from '@emotion/styled';

export type LabelProps = {
  children?: React.ReactNode;
  htmlFor?: string;
  hidden?: boolean;
};

const Label: React.FC<LabelProps> = ({ children, htmlFor, hidden }) => {
  return (
    <Self htmlFor={htmlFor} hidden={hidden}>
      {children}
    </Self>
  );
};

type StyledLabelProps = Pick<LabelProps, 'hidden'>;

export const Self = styled.label<StyledLabelProps>`
  ${({ hidden }) => css`
    ${hidden &&
    css`
      display: block;

      height: 0;
      width: 0;

      visibility: hidden;
    `}
  `}
`;

export default Label;
