import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { type SectionTitleProps } from '@components/section-title/SectionTitle';

type StyledSectionTitleProps = Required<Pick<SectionTitleProps, 'align'>>;

export const SectionTitle = styled.h2<StyledSectionTitleProps>`
  ${({ theme, align }) => css`
    padding: 20px 0;

    font-size: ${theme.fontSize.xl};
    font-weight: ${theme.fontWeight.bold};
    text-align: ${align};
  `}
`;
