import * as S from '@components/letter-counter/LetterCounter.style';

export interface LetterCounterProps {
  count: number;
  maxCount: number;
}

const LetterCounter: React.FC<LetterCounterProps> = ({ count, maxCount }) => {
  return (
    <S.LetterCounter>
      <span>{Math.min(count, maxCount)}</span>/<span>{maxCount}</span>
    </S.LetterCounter>
  );
};

export default LetterCounter;
