import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type PageTitleProps, type SectionTitleProps } from '@design/components/title/Title';

type StylePageTitleProps = Required<Pick<PageTitleProps, 'align'>>;

type StyleSectionTitleProps = Required<Pick<SectionTitleProps, 'align'>>;

export const PageTitle = styled.h1<StylePageTitleProps>`
  ${({ theme, align }) => css`
    padding: 20px 0;

    font-size: ${theme.fontSize.xxl};
    font-weight: ${theme.fontWeight.bold};
    text-align: ${align};
  `}
`;

export const SectionTitle = styled.h2<StyleSectionTitleProps>`
  ${({ theme, align }) => css`
    padding: 20px 0;

    font-size: ${theme.fontSize.xl};
    font-weight: ${theme.fontWeight.bold};
    text-align: ${align};
  `}
`;
