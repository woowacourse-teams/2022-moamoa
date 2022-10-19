import styled from '@emotion/styled';

import { mqDown } from '@styles/responsive';

const sidebarWidth = 280;
const mainGabSidebar = 40;

export const Container = styled.div`
  display: flex;
  column-gap: 40px;

  ${mqDown('md')} {
    flex-direction: column;
    column-gap: 0;
  }
`;

export const Main = styled.div`
  flex-grow: 1;
  max-width: calc(100% - ${mainGabSidebar}px - ${sidebarWidth}px);

  ${mqDown('md')} {
    max-width: 100%;
    margin-bottom: 15px;
  }
`;

export const Sidebar = styled.ul`
  width: 280px;

  & > li {
    margin-bottom: 15px;
  }

  ${mqDown('md')} {
    min-width: 100%;
  }
`;
