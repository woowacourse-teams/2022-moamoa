import { ReactNode, createContext, useState } from 'react';

import { noop } from '@utils';

import AccessTokenController from '@auth/accessTokenController';

type LoginProviderProps = {
  children: ReactNode;
};

type ContextType = {
  isLoggedIn: boolean;
  setIsLoggedIn: React.Dispatch<React.SetStateAction<boolean>>;
};

export const LoginContext = createContext<ContextType>({
  isLoggedIn: false,
  setIsLoggedIn: noop,
});

export const LoginProvider = ({ children }: LoginProviderProps) => {
  const { hasAccessToken, hasTokenDateTime } = AccessTokenController;
  const [isLoggedIn, setIsLoggedIn] = useState(hasAccessToken && hasTokenDateTime);

  return <LoginContext.Provider value={{ isLoggedIn, setIsLoggedIn }}>{children}</LoginContext.Provider>;
};
