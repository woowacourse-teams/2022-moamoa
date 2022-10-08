import { useNavigate } from 'react-router-dom';

import { COMMA, PATH } from '@constants';

import { getRandomInt } from '@utils';

import { type ApiStudy, usePostStudy } from '@api/study';

import { type UseFormSubmitResult, useForm } from '@hooks/useForm';

const useCreateStudyPage = () => {
  const navigate = useNavigate();

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

  const formMethods = useForm();

  const { mutateAsync } = usePostStudy();

  const onSubmit = async (_: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => {
    if (!submitResult.values) return;

    const { values } = submitResult;
    const { feTagId, beTagId } = getAreaTagId();
    const subject = values['subject'].split(COMMA);
    const tagIds = [
      values['area-fe'] === 'checked' ? feTagId : null,
      values['area-be'] === 'checked' ? beTagId : null,
      values['generation'] === '선택 안함' ? null : values['generation'],
      ...subject,
    ]
      .filter(val => val === 0 || !!val)
      .map(Number);

    const thumbnail = `https://picsum.photos/id/${getRandomInt(1, 100)}/200/300`;

    const postData: ApiStudy['post']['body'] = {
      title: values['title'],
      excerpt: values['excerpt'],
      thumbnail,
      description: values['description'],
      maxMemberCount: values['max-member-count'] || null,
      enrollmentEndDate: values['enrollment-end-date'] || null, // nullable
      startDate: values['start-date'],
      endDate: values['end-date'] || null, // nullable
      tagIds,
    };

    // TODO: DetailPage로 Redirect하기
    return mutateAsync(postData, {
      onSuccess: () => {
        alert('스터디를 생성했습니다');
        navigate(PATH.MAIN);
      },
      onError: () => {
        alert('스터디 생성 에러가 발생했습니다');
      },
    });
  };

  return {
    onSubmit,
    formMethods,
  };
};

export default useCreateStudyPage;
