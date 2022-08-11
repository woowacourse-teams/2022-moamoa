import { ReactNode, createContext, useState } from 'react';

import { noop } from '@utils';

type Props = {
  children: ReactNode;
};

type KeywordType = string;

type ContextType = {
  keyword: KeywordType;
  setKeyword: React.Dispatch<React.SetStateAction<KeywordType>>;
};

export const SearchContext = createContext<ContextType>({
  keyword: '',
  setKeyword: noop,
});

export const SearchProvider = ({ children }: Props) => {
  const [keyword, setKeyword] = useState<KeywordType>('');
  return <SearchContext.Provider value={{ keyword, setKeyword }}>{children}</SearchContext.Provider>;
};
