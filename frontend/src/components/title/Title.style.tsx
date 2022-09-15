import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type PageTitleProps, type SectionTitleProps } from '@components/title/Title';

type StyledPageTitleProps = Required<Pick<PageTitleProps, 'align'>>;

type StyledSectionTitleProps = Required<Pick<SectionTitleProps, 'align'>>;

export const PageTitle = styled.h1<StyledPageTitleProps>`
  ${({ theme, align }) => css`
    padding: 20px 0;

    font-size: ${theme.fontSize.xxl};
    font-weight: ${theme.fontWeight.bold};
    text-align: ${align};
  `}
`;

export const SectionTitle = styled.h2<StyledSectionTitleProps>`
  ${({ theme, align }) => css`
    padding: 20px 0;

    font-size: ${theme.fontSize.xl};
    font-weight: ${theme.fontWeight.bold};
    text-align: ${align};
  `}
`;
