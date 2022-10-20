import { Outlet } from 'react-router-dom';

import PageWrapper from '@shared/page-wrapper/PageWrapper';

const CommunityTabPanel: React.FC = () => {
  return (
    <PageWrapper>
      <Outlet />
    </PageWrapper>
  );
};

export default CommunityTabPanel;
