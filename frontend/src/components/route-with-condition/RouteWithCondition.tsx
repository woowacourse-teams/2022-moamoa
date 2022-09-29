import { Navigate, Outlet } from 'react-router-dom';

import { PATH } from '@constants';

export type RouteWithConditionProps = {
  routingCondition: boolean;
};

const RouteWithCondition: React.FC<RouteWithConditionProps> = ({ routingCondition }) => {
  if (!routingCondition) return <Navigate to={PATH.MAIN} replace={true} />;
  return <Outlet />;
};

export default RouteWithCondition;
