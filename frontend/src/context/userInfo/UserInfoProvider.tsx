import { type ReactNode, createContext, useState } from 'react';

import { noop } from '@utils';

import { type Member } from '@custom-types';

type LoginProviderProps = {
  children: ReactNode;
};

type ContextType = {
  userInfo: Member;
  setUserInfo: React.Dispatch<React.SetStateAction<Member>>;
};

const initialValue = { id: -1, username: '', imageUrl: '', profileUrl: '' };

export const UserInfoContext = createContext<ContextType>({
  userInfo: initialValue,
  setUserInfo: noop,
});

export const UserInfoProvider = ({ children }: LoginProviderProps) => {
  const [userInfo, setUserInfo] = useState<Member>(initialValue);
  return <UserInfoContext.Provider value={{ userInfo, setUserInfo }}>{children}</UserInfoContext.Provider>;
};
