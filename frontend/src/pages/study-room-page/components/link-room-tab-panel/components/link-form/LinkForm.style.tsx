import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const LinkFormContainer = styled.div`
  ${({ theme }) => css`
    width: 480px;
    height: 300px;
    padding: 16px;

    background-color: ${theme.colors.white};
    border-radius: 15px;
  `}
`;

export const AuthorInfoContainer = styled.div`
  display: flex;
  align-items: center;

  margin-bottom: 16px;
`;

export const AuthorName = styled.p`
  margin-left: 8px;

  font-weight: 700;
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  row-gap: 8px;
`;

export const FormLabel = styled.label`
  font-weight: 600;
`;

export const FormInput = styled.input`
  ${({ theme }) => css`
    margin-bottom: 8px;
    padding: 8px;

    border: 1px solid ${theme.colors.secondary.base};
    border-radius: 10px;
    background-color: ${theme.colors.secondary.light};
  `}
`;

export const TextAreaContainer = styled.div`
  position: relative;
`;

export const FormTextArea = styled.textarea`
  ${({ theme }) => css`
    width: 100%;
    margin-bottom: 8px;
    padding: 8px;

    border: 1px solid ${theme.colors.secondary.base};
    border-radius: 10px;
    background-color: ${theme.colors.secondary.light};
  `}
`;

export const LetterCounterContainer = styled.div`
  position: absolute;
  bottom: 18px;
  right: 8px;

  display: flex;
  justify-content: flex-end;
`;
