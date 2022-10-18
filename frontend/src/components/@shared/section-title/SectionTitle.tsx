import { css } from '@emotion/react';
import styled from '@emotion/styled';

export type SectionTitleProps = {
  children: React.ReactNode;
  align?: 'left' | 'right' | 'center';
};

const SectionTitle: React.FC<SectionTitleProps> = ({ children, align = 'left' }) => {
  return <Self align={align}>{children}</Self>;
};

export default SectionTitle;

type StyledSectionTitleProps = Required<Pick<SectionTitleProps, 'align'>>;

export const Self = styled.h2<StyledSectionTitleProps>`
  ${({ theme, align }) => css`
    padding: 20px 0;

    font-size: ${theme.fontSize.xl};
    font-weight: ${theme.fontWeight.bold};
    text-align: ${align};
  `}
`;
