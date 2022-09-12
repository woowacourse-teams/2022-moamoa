import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import { itemHoverTransitionStyle } from '@styles/theme';

export const StudyReviewCard = styled.div`
  height: 100%;
  max-height: 150px;
  padding: 8px;

  ${itemHoverTransitionStyle()}
`;

export const Review = styled.p`
  padding: 8px 8px 0;

  ${nLineEllipsis(3)}
`;
