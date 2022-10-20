import { TextButton } from '@shared/button';

export type MoreButtonProps = {
  status: 'fold' | 'unfold';
  foldText: string;
  unfoldText: string;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

const MoreButton: React.FC<MoreButtonProps> = ({ status, foldText, unfoldText, onClick: handleClick }) => {
  return (
    <TextButton variant="secondary" onClick={handleClick}>
      {status === 'fold' ? unfoldText : foldText}
    </TextButton>
  );
};

export default MoreButton;
