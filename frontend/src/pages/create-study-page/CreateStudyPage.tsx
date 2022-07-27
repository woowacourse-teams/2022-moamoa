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
import usePostNewStudy from '@create-study-page/hooks/usePostNewStudy';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { css } from '@emotion/react';

import { StudyDetailPostData } from '@api/postNewStudy';

import { FormProvider, useForm } from '@hooks/useForm';
import type { UseFormSubmitResult } from '@hooks/useForm';

import Wrapper from '@components/wrapper/Wrapper';

function getRandomInt(min: number, max: number) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

const CreateStudyPage: React.FC = () => {
  const formMethods = useForm();

  const navigate = useNavigate();
  const { mutateAsync } = usePostNewStudy();

  const getAreaTagId = () => {
    const areaFeField = formMethods.getField('area-fe')?.fieldElement;
    const areaBeField = formMethods.getField('area-be')?.fieldElement;
    const feTagId = areaFeField?.getAttribute('data-tagid');
    const beTagId = areaBeField?.getAttribute('data-tagid');
    return {
      feTagId,
      beTagId,
    };
  };

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) return;

    const { values } = submitResult;
    const { feTagId, beTagId } = getAreaTagId();
    const tagIds = [
      values['area-fe'] === 'checked' ? feTagId : undefined,
      values['area-be'] === 'checked' ? beTagId : undefined,
      values['generation'],
      values['subject'],
    ]
      .filter(val => val === 0 || !!val)
      .map(Number);

    const thumbnail = `https://picsum.photos/id/${getRandomInt(1, 100)}/200/300`;

    const postData: StudyDetailPostData = {
      title: values['title'],
      excerpt: values['excerpt'],
      thumbnail,
      description: values['description'],
      maxMemberCount: values['max-member-count'],
      enrollmentEndDate: values['enrollment-end-date'], // nullable
      startDate: values['start-date'],
      endDate: values['end-date'], // nullable
      tagIds,
    };

    // TODO: DetailPage로 Redirect하기
    return mutateAsync(postData, {
      onSuccess: () => {
        alert('스터디를 생성했습니다');
        window.location.href = '/';
      },
      onError: () => {
        alert('에러가 발생했습니다');
      },
    });
  };

  useEffect(() => {
    const hasAccessToken = !!window.localStorage.getItem('accessToken');
    if (!hasAccessToken) {
      alert('로그인 후 이용해 주세요');
      navigate('/');
    }
  }, [navigate]);

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
