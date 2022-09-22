import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type PageTitleProps } from '@components/page-title/PageTitle';

type StyledPageTitleProps = Required<Pick<PageTitleProps, 'align'>>;

export const PageTitle = styled.h1<StyledPageTitleProps>`
  ${({ theme, align }) => css`
    padding: 20px 0;

    font-size: ${theme.fontSize.xxl};
    font-weight: ${theme.fontWeight.bold};
    text-align: ${align};
  `}
`;
