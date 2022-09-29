import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import { applyHoverTransitionStyle } from '@styles/theme';

export const StudyReviewCard = styled.div`
  height: 100%;
  max-height: 150px;
  padding: 8px;

  ${applyHoverTransitionStyle()}
`;

export const Review = styled.p`
  padding: 8px 8px 0;

  ${nLineEllipsis(3)}
`;
