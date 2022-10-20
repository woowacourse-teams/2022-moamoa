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
    isFetching,
    isError,
    isSuccess,
  } = useGetUserInformation({
    options: {
      enabled: isLoggedIn,
    },
  });

  // TODO: 이 훅을 사용하는 페이지에서 새로고침했을 때 setUserInfo가 반영되지 않아서 이전 userRole 값을 사용하게 됨
  useEffect(() => {
    if (isFetching || isError || !isSuccess) return;
    setUserInfo(data);
  }, [isFetching]);

  return {
    fetchUserInfo,
    userInfo,
  };
};
