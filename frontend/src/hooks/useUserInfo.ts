import { useContext, useEffect } from 'react';
import { useQuery } from 'react-query';

import type { GetUserInformation } from '@custom-types';

import getUserInformation from '@api/getUserInformation';

import { UserInfoContext } from '@context/userInfo/UserInfoProvider';

export const useUserInfo = () => {
  const { userInfo, setUserInfo } = useContext(UserInfoContext);
  const { data, refetch: fetchUserInfo } = useQuery<GetUserInformation, Error>('user-info', getUserInformation, {
    enabled: false,
  });

  useEffect(() => {
    if (!data) return;
    setUserInfo(data);
  }, [data]);

  return {
    fetchUserInfo,
    userInfo,
  };
};
