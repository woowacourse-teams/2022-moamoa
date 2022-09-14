import * as S from '@components/title/Title.style';

export type PageTitleProps = {
  children: React.ReactNode;
  align?: 'left' | 'right' | 'center';
};

export type SectionTitleProps = PageTitleProps;

const PageTitle: React.FC<PageTitleProps> = ({ children, align = 'left' }) => {
  return <S.PageTitle align={align}>{children}</S.PageTitle>;
};

const SectionTitle: React.FC<SectionTitleProps> = ({ children, align = 'left' }) => {
  return <S.SectionTitle align={align}>{children}</S.SectionTitle>;
};

const Title = {
  Page: PageTitle,
  Section: SectionTitle,
};

export default Title;
