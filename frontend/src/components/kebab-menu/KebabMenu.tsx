import * as S from '@components/kebab-menu/KebabMenu.style';

type KebabMenuProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

const KebabMenu: React.FC<KebabMenuProps> = ({ onClick: handleKebabMenuClick }) => {
  return (
    <S.KebabMenuButton type="button" onClick={handleKebabMenuClick}>
      <S.KebabMenu>
        <li></li>
        <li></li>
        <li></li>
      </S.KebabMenu>
    </S.KebabMenuButton>
  );
};

export default KebabMenu;
