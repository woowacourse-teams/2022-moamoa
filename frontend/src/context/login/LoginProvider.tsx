import { ReactNode, createContext, useState } from 'react';

import noop from '@utils/noop';

interface LoginProviderProps {
  children: ReactNode;
}

interface ContextType {
  isLoggedIn: boolean;
  setIsLoggedIn: React.Dispatch<React.SetStateAction<boolean>>;
}

const hasAccessToken = !!window.localStorage.getItem('accessToken');

export const LoginContext = createContext<ContextType>({
  isLoggedIn: false,
  setIsLoggedIn: noop,
});

export const LoginProvider = ({ children }: LoginProviderProps) => {
  const [isLoggedIn, setIsLoggedIn] = useState(hasAccessToken);
  return <LoginContext.Provider value={{ isLoggedIn, setIsLoggedIn }}>{children}</LoginContext.Provider>;
};
