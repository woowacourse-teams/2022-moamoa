import { useContext, useEffect } from 'react';

import { useGetUserInformation } from '@api/member';

import { useAuth } from '@hooks/useAuth';

import { UserInfoContext } from '@context/userInfo/UserInfoProvider';

export const useUserInfo = () => {
  const { userInfo, setUserInfo } = useContext(UserInfoContext);

  const { isLoggedIn } = useAuth();

  const {
    data,
    refetch: fetchUserInfo,
    isError,
    isSuccess,
  } = useGetUserInformation({
    options: {
      enabled: isLoggedIn,
    },
  });

  useEffect(() => {
    if (!data || isError || !isSuccess) return;
    setUserInfo(data);
  }, [data, isError, isSuccess]);

  return {
    fetchUserInfo,
    userInfo,
  };
};
