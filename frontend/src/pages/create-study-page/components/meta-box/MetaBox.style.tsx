import styled from '@emotion/styled';

export const MetaBox = styled.div`
  background-color: white;
  min-width: 255px;
  border: 1px solid #d0d7de;
  box-shadow: 0 1px 1px rgb(0 0 0 / 4%);
  border-radius: 6px;

  & > .title {
    font-size: 16px;
    padding: 8px 12px;
    margin: 0;
    line-height: 1.4;

    border-bottom: 1px solid #c3c4c7;
  }

  & > .content {
    padding: 8px 12px;
  }
`;
