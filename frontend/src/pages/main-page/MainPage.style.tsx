import styled from '@emotion/styled';

export const CardList = styled.ul`
  display: grid;
  grid-template-columns: repeat(4, minmax(auto, 1fr));
  grid-template-rows: 1fr;
  gap: 32px;
  place-items: center;

  & > li {
    cursor: pointer;
  }

  @media (max-width: 1100px) {
    grid-template-columns: repeat(3, 1fr);
  }
  @media (max-width: 800px) {
    grid-template-columns: repeat(2, 1fr);
  }
  @media (max-width: 500px) {
    grid-template-columns: repeat(1, 256px);
    place-content: center;
  }
`;

export const Page = styled.div``;
