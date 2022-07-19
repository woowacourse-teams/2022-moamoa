import styled from '@emotion/styled';

import { mqDown } from '@utils/media-query';

export const Main = styled.div`
  display: flex;
  column-gap: 40px;

  & > .left {
    width: 100%;
    min-width: 0;
  }
  & > .right {
    min-width: 30%;
    ${mqDown('lg')} {
      display: none;
    }

    & > .sticky {
      position: sticky;
      top: 100px;
      padding-bottom: 20px;
    }
  }
`;

export const ExtraInfo = styled.div`
  & > .title {
    margin-bottom: 30px;
  }
  & > .load-all-reviews-button {
    text-align: center;
  }
  & > .review-list {
    display: flex;
    flex-wrap: wrap;
    column-gap: 60px;

    & > .review {
      width: calc(50% - 30px);
    }

    ${mqDown('md')} {
      flex-direction: column;
      row-gap: 30px;
      & > .review {
        width: 100%;
      }
    }
  }
`;

export const FixedBottomContainer = styled.div`
  display: none;

  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: white;

  padding: 16px 24px;

  background-color: rgb(255, 255, 255);
  border-top: 1px solid rgb(221, 221, 221);

  ${mqDown('lg')} {
    display: block;
  }
`;
