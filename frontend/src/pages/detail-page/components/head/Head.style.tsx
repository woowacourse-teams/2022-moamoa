import styled from '@emotion/styled';

export const Head = styled.div`
  & > .title-container {
    display: flex;
    column-gap: 20px;
    margin-bottom: 20px;
  }
  & > .extra-info-container {
    margin-bottom: 20px;
    & > .review-count {
      margin-right: 20px;
    }
  }
  & > .excerpt {
    margin-bottom: 20px;
  }
  & > .tag-container {
    display: flex;
    column-gap: 15px;
  }
`;
