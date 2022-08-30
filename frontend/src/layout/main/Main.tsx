import { useLocation } from 'react-router-dom';

import * as S from '@layout/main/Main.style';

type MainProps = {
  children: React.ReactNode;
};

const Main: React.FC<MainProps> = ({ children }) => {
  const { pathname } = useLocation();
  const isStudyRoomPage = pathname.split('/')[1] === 'studyroom';
  return <S.Main page={isStudyRoomPage ? 'studyroom' : undefined}>{children}</S.Main>;
};

export default Main;
