import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const StudyFloatBox = styled.div`
  ${({ theme }) => css`
    padding: 40px 40px 30px 40px;

    background: ${theme.colors.white};
    border: 3px solid ${theme.colors.primary.base};
    box-shadow: 8px 8px 0 ${theme.colors.secondary.dark};
    border-radius: 25px;
  `}
`;

export const StudyInfo = styled.div``;

export const EnrollmentEndDate = styled.p`
  ${({ theme }) => css`
    margin-bottom: 20px;

    font-size: ${theme.fontSize.lg};

    & > span {
      padding-right: 4px;

      font-size: ${theme.fontSize.xl};
      font-weight: ${theme.fontWeight.bold};
    }
  `}
`;

export const MemberCount = styled.p`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;

    margin-bottom: 20px;

    & > span {
      font-size: ${theme.fontSize.lg};
    }
  `}
`;

export const Owner = styled.p`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;

    margin-bottom: 20px;

    font-size: ${theme.fontSize.lg};
  `}
`;
