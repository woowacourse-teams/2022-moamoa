import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const StudyMemberCard = styled.div`
  ${({ theme }) => css`
    display: flex;
    align-items: center;

    padding: 12px;

    background: ${theme.colors.secondary.light};
    box-shadow: 0 0 2px 1px ${theme.colors.secondary.base};
    border-radius: 15px;
  `}
`;

export const MemberDescription = styled.div`
  ${({ theme }) => css`
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    row-gap: 4px;

    padding-left: 12px;

    font-size: ${theme.fontSize.sm};
  `}
`;

export const Username = styled.p`
  ${({ theme }) => css`
    font-size: ${theme.fontSize.lg};
    font-weight: ${theme.fontWeight.bold};
  `}
`;

export const UserStudyInfo = styled.p`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;
    column-gap: 20px;

    & > span {
      font-size: ${theme.fontSize.md};
      color: ${theme.colors.secondary.dark};
    }
  `}
`;
