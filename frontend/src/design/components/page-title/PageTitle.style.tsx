import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { PageTitleProps } from './PageTitle';

type StylePageTitleProps = Required<Pick<PageTitleProps, 'align'>>;

export const PageTitle = styled.h1<StylePageTitleProps>`
  ${({ theme, align }) => css`
    margin-bottom: 20px;

    font-size: ${theme.fontSize.xxl};
    font-weight: ${theme.fontWeight.bold};
    text-align: ${align};
  `}
`;
