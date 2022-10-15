import { ReactNode, createContext, useState } from 'react';

import { noop } from '@utils';

import type { UserRole } from '@custom-types';

type LoginProviderProps = {
  children: ReactNode;
};

type ContextType = {
  userRole: UserRole;
  setUserRole: React.Dispatch<React.SetStateAction<UserRole>>;
};

const initialValue = 'NON_MEMBER';

export const UserRoleContext = createContext<ContextType>({
  userRole: initialValue,
  setUserRole: noop,
});

export const UserRoleProvider = ({ children }: LoginProviderProps) => {
  const [userRole, setUserRole] = useState<UserRole>(initialValue);
  return <UserRoleContext.Provider value={{ userRole, setUserRole }}>{children}</UserRoleContext.Provider>;
};
