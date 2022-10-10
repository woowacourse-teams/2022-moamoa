import { useEffect } from 'react';

import type { StudyDetail, Tag } from '@custom-types';

import { useGetTags } from '@api/tags';

import { useFormContext } from '@hooks/useForm';

import Label from '@components/label/Label';
import MetaBox from '@components/meta-box/MetaBox';
import MultiTagSelect, { type MultiTagSelectProps, Option } from '@components/multi-tag-select/MultiTagSelect';

const SUBJECT = 'subject';

const subjectsToOptions = (subjects: StudyDetail['tags']): MultiTagSelectProps['options'] => {
  return subjects.map(({ id, description }) => ({
    label: description,
    value: id,
  }));
};

export type SubjectProps = {
  originalSubjects?: StudyDetail['tags'];
};

const Subject: React.FC<SubjectProps> = ({ originalSubjects }) => {
  const { register } = useFormContext();
  const { data, isLoading, isError, isSuccess } = useGetTags();

  const hasTags = !isError && isSuccess && data.tags.length > 0;

  const originalOptions = originalSubjects ? subjectsToOptions(originalSubjects) : null; // null로 해야 아래쪽 삼항 연산자가 작동합니다

  const subjects = data?.tags?.filter(({ category }) => category.name === SUBJECT);
  const etcTag = subjects?.find(tag => tag.name === 'Etc');

  let selectedOptions: Array<Option> = [];
  if (originalOptions) {
    selectedOptions = originalOptions;
  } else if (etcTag) {
    selectedOptions = [{ value: etcTag.id, label: etcTag.description }];
  }

  const options = subjects ? subjectsToOptions(subjects) : [];

  return (
    <MetaBox>
      <MetaBox.Title>
        <Label htmlFor={SUBJECT}>주제</Label>
      </MetaBox.Title>
      <MetaBox.Content>
        {isLoading && <Loading />}
        {isError && <Error />}
        {isSuccess && !hasTags && <NoTags />}
        {isSuccess && hasTags && !etcTag && <ETCTagError />}
        {isSuccess && hasTags && etcTag && (
          <MultiTagSelect defaultSelectedOptions={selectedOptions} options={options} {...register(SUBJECT)} />
        )}
      </MetaBox.Content>
    </MetaBox>
  );
};

const Loading = () => <div>Loading...</div>;

const Error = () => <div>Error!</div>;

const NoTags = () => <div>선택 가능한 주제(태그)가 없습니다</div>;

const ETCTagError = () => <div>%ERROR%</div>;

export default Subject;
