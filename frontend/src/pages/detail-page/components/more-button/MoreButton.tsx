import * as S from '@detail-page/components/more-button/MoreButton.style';

export type MoreButtonProp = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  status: 'fold' | 'unfold';
  foldText: string;
  unfoldText: string;
};

const MoreButton: React.FC<MoreButtonProp> = ({ onClick, status, foldText, unfoldText }) => {
  return <S.MoreButton onClick={onClick}>{status === 'fold' ? unfoldText : foldText}</S.MoreButton>;
};

export default MoreButton;
