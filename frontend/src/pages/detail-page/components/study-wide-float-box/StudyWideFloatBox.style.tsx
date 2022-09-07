import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const StudyWideFloatBox = styled.div`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;
    column-gap: 16px;

    padding: 10px 20px;

    line-height: 24px;
    background: ${theme.colors.white};
    border-radius: 25px;
  `}
`;

export const StudyInfo = styled.div``;

export const EnrollmentEndDate = styled.p`
  ${({ theme }) => css`
    margin-bottom: 8px;

    & > span {
      font-size: ${theme.fontSize.lg};
      font-weight: ${theme.fontWeight.bold};
    }
  `}
`;

export const MemberCount = styled.p`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;
    column-gap: 16px;

    & > span {
      font-size: ${theme.fontSize.md};
    }
  `}
`;
