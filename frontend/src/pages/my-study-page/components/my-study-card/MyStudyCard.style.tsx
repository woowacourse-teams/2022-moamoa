import { css } from '@emotion/react';
import type { Theme } from '@emotion/react';
import styled from '@emotion/styled';

import { applyHoverTransitionStyle } from '@styles/theme';

import { MyStudyCardProps } from '@my-study-page/components/my-study-card/MyStudyCard';

type StyledMyStudyCardProps = Pick<MyStudyCardProps, 'done'>;

const doneStyle = (theme: Theme) => css`
  & * {
    color: ${theme.colors.secondary.dark} !important;
  }

  & svg {
    stroke: ${theme.colors.secondary.dark} !important;
  }
`;

export const MyStudyCard = styled.div<StyledMyStudyCardProps>`
  ${({ theme, done }) => css`
    position: relative;

    height: 100%;

    ${done && doneStyle(theme)}

    ${applyHoverTransitionStyle()}
  `}
`;
