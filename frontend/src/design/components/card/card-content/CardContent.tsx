import * as S from '@design/components/card/card-content/CardContent.style';

export type CardContentProps = {
  children: React.ReactNode;
  maxLine?: number;
  align?: 'right' | 'left' | 'center';
};

const CardContent: React.FC<CardContentProps> = ({ children, maxLine = 2, align = 'left' }) => {
  return (
    <S.CardContent maxLine={maxLine} align={align}>
      {children}
    </S.CardContent>
  );
};

export default CardContent;
