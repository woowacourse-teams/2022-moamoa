import { useContext, useEffect } from 'react';

import { USER_ROLE } from '@constants';

import { StudyId } from '@custom-types';

import { useGetUserRole } from '@api/member';

import { useAuth } from '@hooks/useAuth';

import { UserRoleContext } from '@context/userRole/UserRoleProvider';

export const useUserRole = ({ studyId }: { studyId: StudyId }) => {
  const { userRole, setUserRole } = useContext(UserRoleContext);

  const { isLoggedIn } = useAuth();

  const {
    data,
    refetch: fetchUserRole,
    isError,
    isSuccess,
  } = useGetUserRole({
    studyId,
    options: {
      enabled: isLoggedIn,
    },
  });

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
