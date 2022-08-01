import * as S from '@layout/header/components/drop-down-box/DropDownBox.style';

export type DropDownBoxProps = {
  children: React.ReactNode;
  onOutOfBoxClick: React.MouseEventHandler<HTMLDivElement>;
  top?: string;
  bottom?: string;
  left?: string;
  right?: string;
};

const DropDownBox: React.FC<DropDownBoxProps> = ({ children, onOutOfBoxClick: handleOutOfBoxClick, ...positions }) => {
  return (
    <S.DropDownBoxContainer onClick={handleOutOfBoxClick}>
      <S.DropDownBox {...positions}>{children}</S.DropDownBox>
    </S.DropDownBoxContainer>
  );
};

export default DropDownBox;
