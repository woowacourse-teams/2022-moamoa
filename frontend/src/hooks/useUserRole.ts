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
    isFetching,
    isError,
    isSuccess,
  } = useGetUserRole({
    studyId,
    options: {
      enabled: isLoggedIn,
    },
  });

  // TODO: 이 훅을 사용하는 페이지에서 새로고침했을 때 setUserRole이 반영되지 않아서 이전 userRole 값을 사용하게 됨
  useEffect(() => {
    if (isFetching || isError || !isSuccess) return;
    setUserRole(data.role);
  }, [isFetching]);

  return {
    fetchUserRole,
    userRole,
    isFetching,
    isError,
    isSuccess,
    isOwner: userRole === USER_ROLE.OWNER,
    isMember: userRole === USER_ROLE.MEMBER,
    isNonMember: userRole === USER_ROLE.NON_MEMBER,
    isOwnerOrMember: userRole !== USER_ROLE.NON_MEMBER,
  };
};
