import * as S from '@study-room-page/tabs/tab-button/TabButton.style';

export type TabButtonProps = {
  className?: string;
  children: string;
  isSelected: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

const TabButton: React.FC<TabButtonProps> = ({ className, children, isSelected, onClick: handleClick }) => {
  return (
    <S.TabButton className={className} type="button" isSelected={isSelected} onClick={handleClick}>
      {children}
    </S.TabButton>
  );
};

export default TabButton;
