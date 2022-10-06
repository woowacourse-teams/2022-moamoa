import * as S from '@components/label/Label.style';

export type LabelProps = {
  children?: React.ReactNode;
  htmlFor?: string;
  hidden?: boolean;
};

const Label: React.FC<LabelProps> = ({ children, htmlFor, hidden }) => {
  return (
    <S.Label htmlFor={htmlFor} hidden={hidden}>
      {children}
    </S.Label>
  );
};

export default Label;
