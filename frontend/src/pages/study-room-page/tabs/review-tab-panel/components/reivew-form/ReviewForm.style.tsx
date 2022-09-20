import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Username = styled.span`
  ${({ theme }) => css`
    font-weight: ${theme.fontWeight.bolder};
  `}
`;

export const UserInfo = styled.a`
  display: flex;
  align-items: center;
  column-gap: 6px;
`;

export const Textarea = styled.textarea`
  ${({ theme }) => css`
    min-height: 90px;
    height: 100%;
    width: 100%;
    font-size: ${theme.fontSize.lg};
    padding: 6px;
    border-color: transparent;

    background-color: transparent;
  `}
`;

export const ReviewFormHead = styled.div`
  padding-top: 12px;
  padding-left: 14px;
`;

export const ReviewFormBody = styled.div`
  padding: 10px 14px;
`;

export const ReviewFormFooter = styled.div`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;
    align-items: center;

    padding: 6px 14px;
    border-top: 1px solid ${theme.colors.secondary.base};

    button {
      border-radius: ${theme.radius.xs};
      padding: 8px 10px;
      font-size: ${theme.fontSize.sm};
    }
  `}
`;

export const ButtonGroup = styled.div``;

export const ReviewForm = styled.form`
  ${({ theme }) => css`
    border: 1px solid ${theme.colors.secondary.dark};
    border-radius: ${theme.radius.xs};
  `}
`;