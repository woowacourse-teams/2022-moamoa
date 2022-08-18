import { ReactNode, createContext, useState } from 'react';

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
  setKeyword: (_: KeywordType | ((_: KeywordType) => KeywordType)) => {},
});

export const SearchProvider = ({ children }: Props) => {
  const [keyword, setKeyword] = useState<KeywordType>('');
  return <SearchContext.Provider value={{ keyword, setKeyword }}>{children}</SearchContext.Provider>;
};
