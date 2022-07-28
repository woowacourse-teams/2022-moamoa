import { ReactNode, createContext, useState } from 'react';

import { ACCESS_TOKEN_KEY } from '@constants';

import { noop } from '@utils/index';

interface LoginProviderProps {
  children: ReactNode;
}

interface ContextType {
  isLoggedIn: boolean;
  setIsLoggedIn: React.Dispatch<React.SetStateAction<boolean>>;
}

const hasAccessToken = !!window.sessionStorage.getItem(ACCESS_TOKEN_KEY);

export const LoginContext = createContext<ContextType>({
  isLoggedIn: false,
  setIsLoggedIn: noop,
});

export const LoginProvider = ({ children }: LoginProviderProps) => {
  const [isLoggedIn, setIsLoggedIn] = useState(hasAccessToken);
  return <LoginContext.Provider value={{ isLoggedIn, setIsLoggedIn }}>{children}</LoginContext.Provider>;
};
