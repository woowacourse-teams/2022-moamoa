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
