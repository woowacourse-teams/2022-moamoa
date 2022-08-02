import { mqDown } from '@utils';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

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

export const Owner = styled.li`
  ${({ theme }) => css`
    position: relative;

    & svg {
      position: absolute;
      top: 5px;
      left: 20px;
      stroke: ${theme.colors.tertiary.base};
      fill: ${theme.colors.tertiary.base};
    }
  `}
`;

export const MoreButtonContainer = styled.div`
  padding: 15px 0;

  text-align: right;
`;
