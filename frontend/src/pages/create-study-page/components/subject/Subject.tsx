import type { StudyDetail } from '@custom-types';

import { useGetTags } from '@api/tags';

import { useFormContext } from '@hooks/useForm';

import Label from '@components/label/Label';
import MetaBox from '@components/meta-box/MetaBox';
import MultiTagSelect, { type MultiTagSelectProps } from '@components/multi-tag-select/MultiTagSelect';

export type SubjectProps = {
  originalSubjects?: StudyDetail['tags'];
};

const SUBJECT = 'subject';

const subjectsToOptions = (subjects: StudyDetail['tags']): MultiTagSelectProps['options'] => {
  return subjects.map(({ id, description }) => ({
    label: description,
    value: id,
  }));
};

const Subject: React.FC<SubjectProps> = ({ originalSubjects }) => {
  const { register } = useFormContext();
  const { data, isLoading, isError, isSuccess } = useGetTags();

  const originalOptions = originalSubjects ? subjectsToOptions(originalSubjects) : null; // null로 해야 아래쪽 삼항 연산자가 작동합니다

  const render = () => {
    if (isLoading) return <div>loading...</div>;

    if (isError || !isSuccess) return <div>Error!</div>;

    const { tags } = data;
    if (tags.length === 0) return <div>선택 가능한 주제(태그)가 없습니다</div>;

    const subjects = tags.filter(({ category }) => category.name === SUBJECT);
    const etcTag = subjects.find(tag => tag.name === 'Etc');
    if (!etcTag) {
      throw new Error('기타 태그의 name이 변경되었습니다');
    }
    const selectedOptions = originalOptions ? originalOptions : [{ value: etcTag.id, label: etcTag.description }];

    const options = subjectsToOptions(subjects);

    return <MultiTagSelect defaultSelectedOptions={selectedOptions} options={options} {...register(SUBJECT)} />;
  };

  return (
    <MetaBox>
      <MetaBox.Title>
        <Label htmlFor={SUBJECT}>주제</Label>
      </MetaBox.Title>
      <MetaBox.Content>{render()}</MetaBox.Content>
    </MetaBox>
  );
};

export default Subject;
