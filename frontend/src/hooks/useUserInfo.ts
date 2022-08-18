import { useContext, useEffect } from 'react';

import { useGetUserInformation } from '@api/member';

import { UserInfoContext } from '@context/userInfo/UserInfoProvider';

export const useUserInfo = () => {
  const { userInfo, setUserInfo } = useContext(UserInfoContext);
  const { data, refetch: fetchUserInfo, isError, isSuccess } = useGetUserInformation();

  useEffect(() => {
    if (!data || isError || !isSuccess) return;
    setUserInfo(data);
  }, [data]);

  return {
    fetchUserInfo,
    userInfo,
  };
};
