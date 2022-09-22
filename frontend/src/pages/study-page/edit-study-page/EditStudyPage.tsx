import { useEffect } from 'react';

import { PATH } from '@constants';

import { FormProvider } from '@hooks/useForm';

import Category from '@pages/study-page/components/category/Category';
import DescriptionTab from '@pages/study-page/components/description-tab/DescriptionTab';
import EnrollmentEndDate from '@pages/study-page/components/enrollment-end-date/EnrollmentEndDate';
import Excerpt from '@pages/study-page/components/excerpt/Excerpt';
import MaxMemberCount from '@pages/study-page/components/max-member-count/MaxMemberCount';
import Period from '@pages/study-page/components/period/Period';
import Publish from '@pages/study-page/components/publish/Publish';
import Subject from '@pages/study-page/components/subject/Subject';
import Title from '@pages/study-page/components/title/Title';
import useEditStudyPage from '@pages/study-page/edit-study-page/hooks/useEditStudyPage';
import { Container, Main, Sidebar } from '@pages/study-page/layout/Layout';

import Form from '@components/form/Form';
import PageTitle from '@components/page-title/PageTitle';
import Wrapper from '@components/wrapper/Wrapper';

const EditStudyPage: React.FC = () => {
  const { studyId, formMethods, onSubmit, navigate, studyQueryResult } = useEditStudyPage();
  const { isError, isSuccess, isFetching, data } = studyQueryResult;

  useEffect(() => {
    if (!studyId) {
      alert('잘못된 접근입니다.');
      navigate(PATH.MAIN);
    }
  }, []);

  if (isFetching) return <div>로딩중...</div>;
  if (isError || !isSuccess) return <div>에러가 발생했습니다 :(</div>;

  // TODO: 반복문 -> useMemo를 사용하는 건 어떨까?
  const originalGeneration = data.tags.find(tag => tag.category.name === 'generation');
  const originalAreas = data.tags.filter(tag => tag.category.name === 'area');
  const originalSubjects = data.tags.filter(tag => tag.category.name === 'subject');

  return (
    <Wrapper>
      <FormProvider {...formMethods}>
        <PageTitle>스터디 수정하기</PageTitle>
        <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
          <Container>
            <Main>
              <Title originalTitle={data.title} />
              <DescriptionTab originalDescription={data.description} />
              <Excerpt originalExcerpt={data.excerpt} />
            </Main>
            <Sidebar>
              <li>
                <Publish title="스터디 수정" buttonText="수정하기" />
              </li>
              <li>
                <MaxMemberCount originalMaxMemberCount={data.maxMemberCount} />
              </li>
              <li>
                <Category originalGeneration={originalGeneration} originalAreas={originalAreas} />
              </li>
              <li>
                <Subject originalSubjects={originalSubjects} />
              </li>
              <li>
                <Period originalStartDate={data.startDate} originalEndDate={data.endDate} />
              </li>
              <li>
                <EnrollmentEndDate originalEnrollmentEndDate={data.enrollmentEndDate} />
              </li>
            </Sidebar>
          </Container>
        </Form>
      </FormProvider>
    </Wrapper>
  );
};

export default EditStudyPage;
