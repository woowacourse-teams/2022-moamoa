import { DraftArtcle } from '@custom-types';

import Divider from '@shared/divider/Divider';
import PageTitle from '@shared/page-title/PageTitle';
import PageWrapper from '@shared/page-wrapper/PageWrapper';
import SectionTitle from '@shared/section-title/SectionTitle';

import DraftList from '@draft-page/components/draft-list/DraftList';

const DraftPage: React.FC = () => {
  return (
    <PageWrapper space="20px">
      <PageTitle align="center">임시 저장 목록</PageTitle>
      <Divider />
      <SectionTitle>커뮤니티 게시글</SectionTitle>
      <DraftList articles={articles} />
    </PageWrapper>
  );
};

export default DraftPage;

const articles: Array<Omit<DraftArtcle, 'content'>> = [
  {
    id: 5,
    title: '자바 게시글 제목4',
    createdDate: '2022-09-02',
    lastModifiedDate: '2022-09-02',
    study: {
      id: 1,
      title: '자바 스터디',
    },
  },
  {
    id: 4,
    title: '자바 게시글 제목3',
    createdDate: '2022-09-02',
    lastModifiedDate: '2022-09-02',
    study: {
      id: 1,
      title: '자바 스터디',
    },
  },
  {
    id: 3,
    title: '자바 게시글 제목2',
    createdDate: '2022-09-02',
    lastModifiedDate: '2022-09-02',
    study: {
      id: 2,
      title: '자바 스터디2 asfsdfsdfsdfsdfsdfsdf',
    },
  },
];
