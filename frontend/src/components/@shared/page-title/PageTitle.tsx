import { css } from '@emotion/react';
import styled from '@emotion/styled';

export type PageTitleProps = {
  children: React.ReactNode;
  align?: 'left' | 'right' | 'center';
};

const PageTitle: React.FC<PageTitleProps> = ({ children, align = 'left' }) => {
  return <Self align={align}>{children}</Self>;
};

export default PageTitle;

type StyledPageTitleProps = Required<Pick<PageTitleProps, 'align'>>;

export const Self = styled.h1<StyledPageTitleProps>`
  ${({ theme, align }) => css`
    padding: 20px 0;

    font-size: ${theme.fontSize.xxl};
    font-weight: ${theme.fontWeight.bold};
    text-align: ${align};
  `}
`;
