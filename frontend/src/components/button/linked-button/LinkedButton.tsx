import { useNavigate } from 'react-router-dom';

import * as S from '@components/button/linked-button/LinkedButton.style';

export type LinkedButtonProps = {
  children: React.ReactNode;
  to: string;
};

const LinkedButton: React.FC<LinkedButtonProps> = ({ children, to }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(to);
  };

  return <S.LinkedButton onClick={handleClick}>{children}</S.LinkedButton>;
};

export default LinkedButton;
