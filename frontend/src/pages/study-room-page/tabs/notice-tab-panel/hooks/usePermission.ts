import { type UserRole } from '@custom-types';

import { useGetUserRole } from '@api/member';

const usePermission = (studyId: string | number, allowedRole: UserRole) => {
  const { data, isFetching, isSuccess, isError } = useGetUserRole({ studyId: Number(studyId) });
  const hasPermission = isSuccess && !isError && data.role === allowedRole;

  return {
    isFetching,
    isError,
    hasPermission,
  };
};

export default usePermission;
