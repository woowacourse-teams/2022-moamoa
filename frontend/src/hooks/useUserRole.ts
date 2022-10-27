import { USER_ROLE } from '@constants';

import { StudyId } from '@custom-types';

import { useGetUserRole } from '@api/member';

import { useAuth } from '@hooks/useAuth';

export const useUserRole = ({ studyId }: { studyId: StudyId }) => {
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

  const userRole = data?.role;

  const isOwner = userRole === USER_ROLE.OWNER;
  const isMember = userRole === USER_ROLE.MEMBER;
  const isNonMember = userRole === USER_ROLE.NON_MEMBER;
  const isOwnerOrMember = isOwner || isMember;

  return {
    fetchUserRole,
    userRole,
    isFetching,
    isError,
    isSuccess,
    isOwner,
    isMember,
    isNonMember,
    isOwnerOrMember,
  };
};
