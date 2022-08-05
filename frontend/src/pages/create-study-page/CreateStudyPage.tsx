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
import Tag from '@create-study-page/components/tag/Tag';
import Title from '@create-study-page/components/title/Title';
import useCreateStudyPage from '@create-study-page/hooks/useCreateStudyPage';

const CreateStudyPage: React.FC = () => {
  const { formMethods, onSubmit } = useCreateStudyPage();

  return (
    <Wrapper>
      <S.CreateStudyPage>
        <FormProvider {...formMethods}>
          <form onSubmit={formMethods.handleSubmit(onSubmit)}>
            <h1 className="title">스터디 개설하기</h1>
            <div className="inner">
              <div className="main">
                <Title />
                <DescriptionTab />
                <Excerpt />
              </div>
              <div className="sidebar">
                <Publish
                  css={css`
                    margin-bottom: 15px;
                  `}
                />
                <MaxMemberCount
                  css={css`
                    margin-bottom: 15px;
                  `}
                />
                <Category
                  css={css`
                    margin-bottom: 15px;
                  `}
                />
                <Tag
                  css={css`
                    margin-bottom: 15px;
                  `}
                />
                <Period
                  css={css`
                    margin-bottom: 15px;
                  `}
                />
                <EnrollmentEndDate />
              </div>
            </div>
          </form>
        </FormProvider>
      </S.CreateStudyPage>
    </Wrapper>
  );
};

export default CreateStudyPage;
