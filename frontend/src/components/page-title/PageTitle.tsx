import * as S from '@components/page-title/PageTitle.style';

export type PageTitleProps = {
  children: React.ReactNode;
  align?: 'left' | 'right' | 'center';
};

const PageTitle: React.FC<PageTitleProps> = ({ children, align = 'left' }) => {
  return <S.PageTitle align={align}>{children}</S.PageTitle>;
};

export default PageTitle;
