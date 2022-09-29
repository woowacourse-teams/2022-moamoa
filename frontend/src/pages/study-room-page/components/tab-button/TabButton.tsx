import { ToggleButton } from '@components/button';

export type TabButtonProps = {
  children: string;
  isSelected: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

const TabButton: React.FC<TabButtonProps> = ({ children, isSelected, onClick: handleClick }) => {
  return (
    <ToggleButton checked={isSelected} onClick={handleClick} variant="secondary" fluid fontSize="lg">
      {children}
    </ToggleButton>
  );
};

export default TabButton;
