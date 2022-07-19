import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const StudyWideFloatBox = styled.div`
  ${({ theme }) => css`
    display: flex;
    line-height: 24px;
    & > .left {
      width: 100%;
      & > .deadline {
        font-size: 18px;
      }
      & > .member-count {
        font-size: 14px;
      }
    }
    & .right {
    }
  `}
`;
