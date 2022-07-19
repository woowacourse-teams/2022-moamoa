import styled from '@emotion/styled';

import { mqDown } from '@utils/media-query';

export const StudyMemberList = styled.div`
  .title {
    margin-bottom: 30px;
  }
  .member-list {
    display: grid;
    grid-template-columns: repeat(2, minmax(auto, 1fr));
    grid-column-gap: 30px;
    grid-row-gap: 20px;

    ${mqDown('md')} {
      display: flex;
      flex-direction: column;
      row-gap: 20px;
    }
  }
  .more-button-container {
    text-align: center;
    padding-top: 30px;
    padding-bottom: 15px;
  }
`;
