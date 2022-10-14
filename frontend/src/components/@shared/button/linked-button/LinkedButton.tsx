// LinkButton의 필요성
// Link만 사용했을 경우 내부에 있는 버튼 핸들러가 작동하기 전에 페이지 이동이 됩니다.
import { useNavigate } from 'react-router-dom';

import styled from '@emotion/styled';

export type LinkedButtonProps = {
  children: React.ReactNode;
  to: string;
};

const LinkedButton: React.FC<LinkedButtonProps> = ({ children, to }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(to);
  };

  return <Self onClick={handleClick}>{children}</Self>;
};

const Self = styled.button`
  width: 100%;
  height: fit-content;
  background: transparent;
  border: none;
  text-align: unset;
`;

export default LinkedButton;
