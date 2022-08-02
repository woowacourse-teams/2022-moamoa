import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const ReviewSection = styled.section`
  padding: 16px;

  border-radius: 15px;
`;

export const ReviewTitle = styled.h3`
  margin-bottom: 30px;

  font-size: 24px;
  font-weight: 700;

  & > span {
    font-size: 16px;
  }
`;

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

export const MoreButtonContainer = styled.div`
  padding: 16px 0;

  text-align: right;
`;
