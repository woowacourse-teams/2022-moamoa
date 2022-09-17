import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const ReviewList = styled.ul`
  display: flex;
  flex-wrap: wrap;
  gap: 16px 60px;

  ${mqDown('md')} {
    flex-direction: column;
    row-gap: 30px;
  }
`;

export const ReviewListItem = styled.li`
  width: calc(50% - 30px);

  ${mqDown('md')} {
    width: 100%;
  }
`;
