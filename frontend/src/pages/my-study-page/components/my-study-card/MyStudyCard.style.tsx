import { css } from '@emotion/react';
import type { Theme } from '@emotion/react';
import styled from '@emotion/styled';

import { itemHoverTransitionStyle } from '@styles/theme';

import { MyStudyCardProps } from '@my-study-page/components/my-study-card/MyStudyCard';

type StyleMyStudyCardProps = Pick<MyStudyCardProps, 'end'>;

const endedStyle = (theme: Theme) => css`
  & * {
    color: ${theme.colors.secondary.dark} !important;
  }

  & svg {
    stroke: ${theme.colors.secondary.dark} !important;
  }
`;

export const MyStudyCard = styled.div<StyleMyStudyCardProps>`
  ${({ theme, end }) => css`
    position: relative;

    height: 100%;

    ${end && endedStyle(theme)}

    ${itemHoverTransitionStyle()}
  `}
`;
