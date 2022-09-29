import * as S from '@components/section-title/SectionTitle.style';

export type SectionTitleProps = {
  children: React.ReactNode;
  align?: 'left' | 'right' | 'center';
};

const SectionTitle: React.FC<SectionTitleProps> = ({ children, align = 'left' }) => {
  return <S.SectionTitle align={align}>{children}</S.SectionTitle>;
};

export default SectionTitle;
