import * as S from '@layout/header/components/drop-down-box/DropDownBox.style';

export interface DropDownBoxProps {
  children: React.ReactNode;
  onOutOfBoxClick: React.MouseEventHandler<HTMLDivElement>;
  top?: string;
  bottom?: string;
  left?: string;
  right?: string;
}

const DropDownBox: React.FC<DropDownBoxProps> = ({ children, onOutOfBoxClick, ...positions }) => {
  return (
    <S.DropDownBoxContainer onClick={onOutOfBoxClick}>
      <S.DropDownBox {...positions}>{children}</S.DropDownBox>
    </S.DropDownBoxContainer>
  );
};

export default DropDownBox;
