import { useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import { getRandomInt } from '@utils';

import type { PostNewStudyRequestBody } from '@custom-types';

import { useForm } from '@hooks/useForm';
import type { UseFormSubmitResult } from '@hooks/useForm';

import usePostNewStudy from '@create-study-page/hooks/usePostNewStudy';

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

  const { mutateAsync } = usePostNewStudy();

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

    const postData: PostNewStudyRequestBody = {
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
