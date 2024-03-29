import Category from '@study-page/components/category/Category';
import DescriptionTab from '@study-page/components/description-tab/DescriptionTab';
import EnrollmentEndDate from '@study-page/components/enrollment-end-date/EnrollmentEndDate';
import Excerpt from '@study-page/components/excerpt/Excerpt';
import MaxMemberCount from '@study-page/components/max-member-count/MaxMemberCount';
import Period from '@study-page/components/period/Period';
import Publish from '@study-page/components/publish/Publish';
import Subject from '@study-page/components/subject/Subject';
import Title from '@study-page/components/title/Title';
import useEditStudyPage from '@study-page/edit-study-page/hooks/useEditStudyPage';
import { Container, Main, Sidebar } from '@study-page/layout/Layout';
import { useEffect } from 'react';

import { CATEGORY_NAME, PATH } from '@constants';

import { FormProvider } from '@hooks/useForm';

import Form from '@shared/form/Form';
import PageTitle from '@shared/page-title/PageTitle';
import PageWrapper from '@shared/page-wrapper/PageWrapper';

const EditStudyPage: React.FC = () => {
  const { studyId, formMethods, onSubmit, navigate, studyQueryResult } = useEditStudyPage();
  const { isError, isSuccess, isFetching, data } = studyQueryResult;

  useEffect(() => {
    if (!studyId) {
      alert('잘못된 접근입니다.');
      navigate(PATH.MAIN, { replace: true });
    }
  }, []);

  if (isFetching) return <div>로딩중...</div>;
  if (isError || !isSuccess) return <div>에러가 발생했습니다 :(</div>;

  // TODO: 반복문 -> useMemo를 사용하는 건 어떨까?
  const originalGeneration = data.tags.find(tag => tag.category.name === CATEGORY_NAME.GENERATION);
  const originalAreas = data.tags.filter(tag => tag.category.name === CATEGORY_NAME.AREA);
  const originalSubjects = data.tags.filter(tag => tag.category.name === CATEGORY_NAME.SUBJECT);

  return (
    <PageWrapper>
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
    </PageWrapper>
  );
};

export default EditStudyPage;
