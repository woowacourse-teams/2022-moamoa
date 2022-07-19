import styled from '@emotion/styled';

export const Head = styled.div`
  & > .title-container {
    display: flex;
    align-items: center;
    column-gap: 20px;
    margin-bottom: 20px;
    h1 {
      font-size: 40px;
    }
    .chip {
    }
  }

  & > .extra-info-container {
    font-size: 20px;
    margin-bottom: 20px;
    & > .review-count {
      margin-right: 20px;
    }
  }

  & > .excerpt {
    margin-bottom: 20px;
    font-size: 28px;
  }

  & > .tag-container {
    display: flex;
    column-gap: 15px;
  }
`;
