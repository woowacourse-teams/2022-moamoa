import { useEffect } from 'react';

import { PATH } from '@constants';

import { FormProvider } from '@hooks/useForm';

import Form from '@components/form/Form';
import PageTitle from '@components/title/Title';
import Wrapper from '@components/wrapper/Wrapper';

import Category from '@create-study-page/components/category/Category';
import DescriptionTab from '@create-study-page/components/description-tab/DescriptionTab';
import EnrollmentEndDate from '@create-study-page/components/enrollment-end-date/EnrollmentEndDate';
import Excerpt from '@create-study-page/components/excerpt/Excerpt';
import MaxMemberCount from '@create-study-page/components/max-member-count/MaxMemberCount';
import Period from '@create-study-page/components/period/Period';
import Publish from '@create-study-page/components/publish/Publish';
import Subject from '@create-study-page/components/subject/Subject';
import Title from '@create-study-page/components/title/Title';

import * as S from '@edit-study-page/EditStudyPage.style';
import useEditStudyPage from '@edit-study-page/hooks/useEditStudyPage';

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

  return (
    <Wrapper>
      <FormProvider {...formMethods}>
        <PageTitle.Page>스터디 수정하기</PageTitle.Page>
        <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
          <S.Container>
            <S.Main>
              <Title originalTitle={data.title} />
              <DescriptionTab originalDescription={data.description} />
              <Excerpt originalExcerpt={data.excerpt} />
            </S.Main>
            <S.Sidebar>
              <li>
                <Publish title="스터디 수정" buttonText="수정하기" />
              </li>
              <li>
                <MaxMemberCount originalMaxMemberCount={data.maxMemberCount} />
              </li>
              <li>
                <Category
                  originalGeneration={data.tags.find(tag => tag.category.name === 'generation')}
                  originalAreas={data.tags.filter(tag => tag.category.name === 'area')}
                />
              </li>
              <li>
                <Subject originalSubjects={data.tags.filter(tag => tag.category.name === 'subject')} />
              </li>
              <li>
                <Period originalStartDate={data.startDate} originalEndDate={data.endDate} />
              </li>
              <li>
                <EnrollmentEndDate originalEnrollmentEndDate={data.enrollmentEndDate} />
              </li>
            </S.Sidebar>
          </S.Container>
        </Form>
      </FormProvider>
    </Wrapper>
  );
};

export default EditStudyPage;
