import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const StudyWideFloatBox = styled.div`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;
    column-gap: 16px;

    padding: 15px;

    line-height: 24px;
    font-size: 24px;
    background: ${theme.colors.white};
    border-radius: 25px;
  `}
`;

export const StudyInfo = styled.div``;

export const ExtraInfo = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Deadline = styled.p`
  margin-bottom: 10px;

  font-size: 20px;

  & > span {
    padding-right: 4px;

    font-size: 28px;
    font-weight: 700;
  }
`;

export const MemberCount = styled.p`
  display: flex;
  justify-content: space-between;

  & > span {
    font-size: 20px;
  }
`;

export const Owner = styled.p`
  display: flex;
  justify-content: space-between;

  font-size: 20px;
`;
