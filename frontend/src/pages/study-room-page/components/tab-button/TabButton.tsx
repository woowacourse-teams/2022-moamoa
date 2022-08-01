import * as S from '@study-room-page/components/tab-button/TabButton.style';

export interface TabButtonProps {
  className?: string;
  children: string;
  isSelected: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
}

const TabButton: React.FC<TabButtonProps> = ({ className, children, isSelected, onClick }) => {
  return (
    <S.TabButton className={className} type="button" isSelected={isSelected} onClick={onClick}>
      {children}
    </S.TabButton>
  );
};

export default TabButton;
