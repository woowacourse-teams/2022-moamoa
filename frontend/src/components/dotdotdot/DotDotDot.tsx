import * as S from '@components/dotdotdot/DotDotDot.style';

type DotDotDotProps = {
  onClick: React.MouseEventHandler<HTMLUListElement>;
};

const DotDotDot: React.FC<DotDotDotProps> = ({ onClick }) => {
  return (
    <S.DotDotDot onClick={onClick}>
      <li></li>
      <li></li>
      <li></li>
    </S.DotDotDot>
  );
};

export default DotDotDot;
