import * as S from '@create-study-page/CreateStudyPage.style';

import { css } from '@emotion/react';

import { FormProvider, useForm } from '@hooks/useForm';
import type { UseFormSubmitResult } from '@hooks/useForm';

import Wrapper from '@components/wrapper/Wrapper';

import Category from './components/category/Category';
import DescriptionTab from './components/description-tab/DescriptionTab';
import EnrollmentEndDate from './components/enrollment-end-date/EnrollmentEndDate';
import Excerpt from './components/excerpt/Excerpt';
import MaxMemberCount from './components/max-member-count/MaxMemberCount';
import Period from './components/period/Peroid';
import Publish from './components/publish/Publish';
import Tag from './components/tag/Tag';

const CreateStudyPage: React.FC = () => {
  const formMethods = useForm();

  const onSubmit = (event: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    return new Promise((resolve, reject) => {
      console.log('values : ', submitResult.values);
    });
  };

  return (
    <Wrapper>
      <S.CreateStudyPage>
        <FormProvider {...formMethods}>
          <form onSubmit={formMethods.handleSubmit(onSubmit)}>
            <h1 className="title">스터디 개설하기</h1>
            <div className="inner">
              <div className="main">
                <input
                  className="title-input"
                  type="text"
                  placeholder="스터디 이름"
                  {...formMethods.register('title')}
                />
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
