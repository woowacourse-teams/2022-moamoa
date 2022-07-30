import * as S from '@study-room-page/components/tab-button/TabButton.style';

export interface TabButtonProps {
  className?: string;
  children: string;
  id: string;
  isSelected: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
}

const TabButton: React.FC<TabButtonProps> = ({ className, children, id, isSelected, onClick }) => {
  return (
    <S.TabButton className={className} type="button" id={id} isSelected={isSelected} onClick={onClick}>
      {children}
    </S.TabButton>
  );
};

export default TabButton;
