import { css } from '@emotion/react';

import { FormProvider } from '@hooks/useForm';

import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@create-study-page/CreateStudyPage.style';
import Category from '@create-study-page/components/category/Category';
import DescriptionTab from '@create-study-page/components/description-tab/DescriptionTab';
import EnrollmentEndDate from '@create-study-page/components/enrollment-end-date/EnrollmentEndDate';
import Excerpt from '@create-study-page/components/excerpt/Excerpt';
import MaxMemberCount from '@create-study-page/components/max-member-count/MaxMemberCount';
import Period from '@create-study-page/components/period/Peroid';
import Publish from '@create-study-page/components/publish/Publish';
import Subject from '@create-study-page/components/subject/Subject';
import Title from '@create-study-page/components/title/Title';
import useCreateStudyPage from '@create-study-page/hooks/useCreateStudyPage';

const CreateStudyPage: React.FC = () => {
  const { formMethods, onSubmit } = useCreateStudyPage();

  return (
    <Wrapper>
      <S.CreateStudyPage>
        <FormProvider {...formMethods}>
          <S.Form onSubmit={formMethods.handleSubmit(onSubmit)}>
            <h1>스터디 개설하기</h1>
            <S.Container>
              <S.Main>
                <Title />
                <DescriptionTab css={css``} />
                <Excerpt />
              </S.Main>
              <S.Sidebar>
                <li>
                  <Publish />
                </li>
                <li>
                  <MaxMemberCount />
                </li>
                <li>
                  <Category />
                </li>
                <li>
                  <Subject />
                </li>
                <li>
                  <Period />
                </li>
                <li>
                  <EnrollmentEndDate />
                </li>
              </S.Sidebar>
            </S.Container>
          </S.Form>
        </FormProvider>
      </S.CreateStudyPage>
    </Wrapper>
  );
};

export default CreateStudyPage;
