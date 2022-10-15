import { useContext, useEffect } from 'react';

import { USER_ROLE } from '@constants';

import { type ApiUserRole, useGetUserRole } from '@api/member';

import { UserRoleContext } from '@context/userRole/UserRoleProvider';

export const useUserRole = ({ studyId, options }: ApiUserRole['get']['variables']) => {
  const { userRole, setUserRole } = useContext(UserRoleContext);
  const { data, refetch: fetchUserRole, isError, isSuccess } = useGetUserRole({ studyId, options });

  useEffect(() => {
    if (!data || isError || !isSuccess) return;
    setUserRole(data.role);
  }, [data, isError, isSuccess]);

  return {
    fetchUserRole,
    userRole,
    isOwner: userRole === USER_ROLE.OWNER,
    isMember: userRole === USER_ROLE.MEMBER,
    isNonMember: userRole === USER_ROLE.NON_MEMBER,
    isOwnerOrMember: userRole !== USER_ROLE.NON_MEMBER,
  };
};
