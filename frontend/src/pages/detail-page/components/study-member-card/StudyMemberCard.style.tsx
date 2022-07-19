import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const StudyMemberCard = styled.div`
  ${({ theme }) => css`
    display: flex;
    padding: 12px;
    background: ${theme.colors.secondary.light};
    box-shadow: 0px 0px 2px 1px ${theme.colors.secondary.base};
    border-radius: 15px;
    align-items: center;

    & > .right {
      display: flex;
      flex-direction: column;
      padding-left: 12px;
      font-size: 12px;

      .username {
        font-size: 18px;
      }
      .study-count {
        margin-right: 12px;
      }
    }
  `}
`;
