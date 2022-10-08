import Category from '@study-page/components/category/Category';
import DescriptionTab from '@study-page/components/description-tab/DescriptionTab';
import EnrollmentEndDate from '@study-page/components/enrollment-end-date/EnrollmentEndDate';
import Excerpt from '@study-page/components/excerpt/Excerpt';
import MaxMemberCount from '@study-page/components/max-member-count/MaxMemberCount';
import Period from '@study-page/components/period/Period';
import Publish from '@study-page/components/publish/Publish';
import Subject from '@study-page/components/subject/Subject';
import Title from '@study-page/components/title/Title';
import useCreateStudyPage from '@study-page/create-study-page/hooks/useCreateStudyPage';
import { Container, Main, Sidebar } from '@study-page/layout/Layout';

import { FormProvider } from '@hooks/useForm';

import Form from '@components/form/Form';
import PageTitle from '@components/page-title/PageTitle';
import PageWrapper from '@components/page-wrapper/PageWrapper';

const CreateStudyPage: React.FC = () => {
  const { formMethods, onSubmit } = useCreateStudyPage();

  return (
    <PageWrapper>
      <FormProvider {...formMethods}>
        <PageTitle>스터디 개설하기</PageTitle>
        <Form onSubmit={formMethods.handleSubmit(onSubmit)}>
          <Container>
            <Main>
              <Title />
              <DescriptionTab />
              <Excerpt />
            </Main>
            <Sidebar>
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
            </Sidebar>
          </Container>
        </Form>
      </FormProvider>
    </PageWrapper>
  );
};

export default CreateStudyPage;
