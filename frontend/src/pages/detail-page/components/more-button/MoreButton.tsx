import * as S from '@detail-page/components/more-button/MoreButton.style';

export type MoreButtonProps = {
  status: 'fold' | 'unfold';
  foldText: string;
  unfoldText: string;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};

const MoreButton: React.FC<MoreButtonProps> = ({ status, foldText, unfoldText, onClick: handleClick }) => {
  return <S.MoreButton onClick={handleClick}>{status === 'fold' ? unfoldText : foldText}</S.MoreButton>;
};

export default MoreButton;
