import styled from '@emotion/styled';

import { mqDown } from '@utils/index';

export const StudyMemberSection = styled.section`
  padding: 16px;

  border-radius: 15px;
`;

export const Title = styled.h3`
  margin-bottom: 30px;

  font-size: 24px;
  font-weight: 700;

  & > span {
    font-size: 16px;
  }
`;

export const MemberList = styled.ul`
  display: grid;
  grid-template-columns: repeat(2, minmax(auto, 1fr));
  grid-column-gap: 30px;
  grid-row-gap: 20px;

  ${mqDown('md')} {
    display: flex;
    flex-direction: column;
    row-gap: 20px;
  }
`;

export const MoreButtonContainer = styled.div`
  padding: 15px 0;

  text-align: right;
`;
