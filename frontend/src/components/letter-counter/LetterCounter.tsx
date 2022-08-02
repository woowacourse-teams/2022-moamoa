import * as S from '@components/letter-counter/LetterCounter.style';

export type LetterCounterProps = {
  count: number;
  maxCount: number;
};

const LetterCounter: React.FC<LetterCounterProps> = ({ count, maxCount }) => {
  return (
    <S.Container>
      <span>{Math.min(count, maxCount)}</span>/<span>{maxCount}</span>
    </S.Container>
  );
};

export default LetterCounter;
