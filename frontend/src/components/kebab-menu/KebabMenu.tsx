import * as S from '@components/kebab-menu/KebabMenu.style';

type KebabMenuProps = {
  onClick: React.MouseEventHandler<HTMLUListElement>;
};

const KebabMenu: React.FC<KebabMenuProps> = ({ onClick }) => {
  return (
    <S.KebabMenu onClick={onClick}>
      <li></li>
      <li></li>
      <li></li>
    </S.KebabMenu>
  );
};

export default KebabMenu;
