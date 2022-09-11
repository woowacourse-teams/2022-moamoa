import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

export const StudyReviewCard = styled.div`
  height: 100%;
  max-height: 150px;
  padding: 8px;

  transition: transform 0.2s ease;

  :hover {
    opacity: 0.9;
    transform: translate3d(0, -5px, 0);
  }
`;

export const Review = styled.p`
  padding: 8px 8px 0;

  ${nLineEllipsis(3)}
`;
