import * as S from './Button.style';

export type ButtonProp = {
  children: string;
  onClick: () => void;
};

const Button: React.FC<ButtonProp> = ({ children, onClick }) => {
  return <S.Button onClick={onClick}>{children}</S.Button>;
};

export default Button;
