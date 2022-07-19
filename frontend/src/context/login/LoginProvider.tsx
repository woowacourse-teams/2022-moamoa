import { ReactNode, createContext, useState } from 'react';

interface LoginProviderProps {
  children: ReactNode;
}

interface ContextType {
  isLoggedIn: boolean;
  setIsLoggedIn: React.Dispatch<React.SetStateAction<boolean>>;
}

export const LoginContext = createContext<ContextType>({
  isLoggedIn: !!localStorage.getItem('accessToken'),
  setIsLoggedIn: () => {},
});

export const LoginProvider = ({ children }: LoginProviderProps) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  return <LoginContext.Provider value={{ isLoggedIn, setIsLoggedIn }}>{children}</LoginContext.Provider>;
};
