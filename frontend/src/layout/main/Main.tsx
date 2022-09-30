import styled from '@emotion/styled';

import { mqDown } from '@styles/responsive';

export type MainProps = {
  children: React.ReactNode;
};

const Main: React.FC<MainProps> = ({ children }) => <Self>{children}</Self>;

export const Self = styled.main`
  padding: 120px 0 80px;
  min-height: calc(100vh - 80px);
  height: auto;

  ${mqDown('md')} {
    padding-top: 90px;
  }

  ${mqDown('sm')} {
    padding-top: 80px;
  }
`;

export default Main;
