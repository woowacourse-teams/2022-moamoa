import { useNavigate, useParams } from 'react-router-dom';

import { COMMA, PATH } from '@constants';

import { getRandomInt } from '@utils';

import { type ApiStudy, useGetStudy, usePutStudy } from '@api/study';

import { type UseFormSubmitResult, useForm } from '@hooks/useForm';

const useEditStudyPage = () => {
  const { studyId } = useParams<{ studyId: string }>();
  const navigate = useNavigate();

  const formMethods = useForm();

  const studyQueryResult = useGetStudy({ studyId: Number(studyId) });
  const { mutateAsync } = usePutStudy();

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
    const { values, errors } = submitResult;
    if (!values || !errors) return;

    if (Object.values(errors).some(error => error.hasError)) {
      const error = Object.values(errors).find(error => error.hasError);
      error && alert(error.errorMessage);
      return;
    }

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

    const putData: ApiStudy['put']['body'] = {
      title: values['title'],
      excerpt: values['excerpt'],
      thumbnail,
      description: values['description'],
      maxMemberCount: values['max-member-count'] || null,
      enrollmentEndDate: values['enrollment-end-date'] || null,
      startDate: values['start-date'],
      endDate: values['end-date'] || null,
      tagIds,
    };

    // TODO: DetailPage로 Redirect하기
    return mutateAsync(
      { studyId: Number(studyId), editedStudy: putData },
      {
        onSuccess: () => {
          alert('스터디를 수정했습니다');
          navigate(PATH.MAIN, { replace: true });
        },
        onError: () => {
          alert('스터디 생성 에러가 발생했습니다');
        },
      },
    );
  };

  return {
    studyId,
    onSubmit,
    navigate,
    formMethods,
    studyQueryResult,
  };
};

export default useEditStudyPage;
