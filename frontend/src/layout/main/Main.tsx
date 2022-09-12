import * as S from '@layout/main/Main.style';

export type MainProps = {
  children: React.ReactNode;
};

const Main: React.FC<MainProps> = ({ children }) => <S.Main>{children}</S.Main>;

export default Main;
