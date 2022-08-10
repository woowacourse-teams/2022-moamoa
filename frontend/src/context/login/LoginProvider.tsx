import { ReactNode, createContext, useEffect, useState } from 'react';

import { noop } from '@utils';

import AccessTokenController from '@auth/accessToken';

import { useUserInfo } from '@hooks/useUserInfo';

type LoginProviderProps = {
  children: ReactNode;
};

type ContextType = {
  isLoggedIn: boolean;
  setIsLoggedIn: React.Dispatch<React.SetStateAction<boolean>>;
};

const hasAccessToken = !!AccessTokenController.accessToken;

export const LoginContext = createContext<ContextType>({
  isLoggedIn: false,
  setIsLoggedIn: noop,
});

export const LoginProvider = ({ children }: LoginProviderProps) => {
  const [isLoggedIn, setIsLoggedIn] = useState(hasAccessToken);
  const { fetchUserInfo } = useUserInfo();

  useEffect(() => {
    if (isLoggedIn) {
      fetchUserInfo();
    }
  }, []);

  return <LoginContext.Provider value={{ isLoggedIn, setIsLoggedIn }}>{children}</LoginContext.Provider>;
};
