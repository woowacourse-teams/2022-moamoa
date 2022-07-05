import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Card = styled.div`
  ${({ theme }) => css`
    display: flex;
    flex-direction: column;

    width: 100%;
    height: 350px;
    overflow: hidden;

    border: 3px solid ${theme.colors.primary.base};
    border-radius: 25px;
    box-shadow: 8px 8px 0 0 ${theme.colors.secondary.dark};

    :hover {
      opacity: 0.9;
    }
  `}
`;

export const ImageContainer = styled.div`
  width: 100%;

  flex-grow: 1;

  overflow: hidden;
`;

export const Image = styled.img`
  width: 100%;
  height: 100%;

  object-fit: cover;
  object-position: center;
`;

export const Contents = styled.div`
  padding: 8px 8px 12px;

  background-color: ${({ theme }) => theme.colors.secondary.light};
`;

export const Title = styled.h4`
  padding-bottom: 20px;

  font-size: 24px;
  font-weight: 700;
`;

export const Description = styled.p`
  width: 100%;
  font-size: 20px;
  margin-bottom: 20px;

  overflow: clip;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  word-break: break-all;
  line-height: 20px;
`;

export const Extra = styled.div`
  display: flex;
  justify-content: flex-end;
`;
