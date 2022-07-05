import styled from '@emotion/styled';

export const ImageContainer = styled.div`
  ${({ theme }) => `
    width: 36px;
    min-width: 36px;
    height: 36px;
    border-radius: 50%;
    box-shadow: 0px 1px 5px 0px ${theme.colors.secondary.dark};
    overflow: hidden;
    transition: opacity 0.2s ease;

    &:hover,
    &:active {
      opacity: 0.9;
    }
  `}
`;

export const Image = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
`;
