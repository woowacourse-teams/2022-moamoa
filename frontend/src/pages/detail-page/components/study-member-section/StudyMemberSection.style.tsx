import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { mqDown } from '@utils';

export const StudyMemberSection = styled.section`
  padding: 16px;

  border-radius: ${theme.radius.md};
`;

export const Title = styled.h3`
  ${({ theme }) => css`
    margin-bottom: 30px;

    font-size: ${theme.fontSize.xl};
    font-weight: ${theme.fontWeight.bold};

    & > span {
      font-size: ${theme.fontSize.md};
    }
  `}
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
