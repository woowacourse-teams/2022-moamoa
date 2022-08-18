import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Head = styled.div`
  display: flex;
  flex-direction: column;
  row-gap: 12px;
`;

export const TitleContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const Title = styled.div`
  display: flex;
  align-items: center;
  column-gap: 20px;
`;

export const StudyTitle = styled.h2`
  font-size: 40px;
  font-weight: 700;
`;

export const ButtonsContainer = styled.div``;

export const Button = styled.button`
  ${({ theme }) => css`
    margin-left: 8px;
    padding: 8px;

    font-size: 20px;
    font-weight: 600;
    color: ${theme.colors.secondary.dark};
    background-color: transparent;
    border: none;
    border-radius: 5px;
    transition: color 0.1s ease;

    &:hover,
    &:active {
      color: ${theme.colors.primary.base};
    }
  `}
`;

export const ExtraInfoContainer = styled.div`
  display: flex;
  align-items: center;
  column-gap: 16px;

  font-weight: 300;
`;

export const Excerpt = styled.p`
  padding: 8px 0 16px;

  font-size: 28px;
`;

export const TagContainer = styled.div`
  display: flex;
  column-gap: 16px;
`;
