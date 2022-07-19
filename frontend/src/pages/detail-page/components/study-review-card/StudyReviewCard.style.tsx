import styled from '@emotion/styled';

export const StudyReviewCard = styled.div`
  .author {
    display: flex;
    align-items: center;
    margin-bottom: 10px;

    & > .left {
    }

    & > .right {
      padding-left: 12px;
      .username {
        margin-bottom: 4px;
      }
      .review-date {
        color: #717171;
        font-size: 14px;
      }
    }
  }
  .review {
    display: -webkit-box;
    line-height: 24px;
    -webkit-line-clamp: 3;
    overflow: hidden;
    text-overflow: ellipsis;
    -webkit-box-orient: vertical;
    margin-bottom: 40px;
  }
`;
