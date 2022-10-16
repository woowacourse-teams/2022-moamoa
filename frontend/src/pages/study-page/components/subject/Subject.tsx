import type { StudyDetail } from '@custom-types';

import { useGetTags } from '@api/tags';

import { useFormContext } from '@hooks/useForm';

import Label from '@shared/label/Label';
import MetaBox from '@shared/meta-box/MetaBox';
import MultiTagSelect, { type MultiTagSelectProps, Option } from '@shared/multi-tag-select/MultiTagSelect';

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
        {(() => {
          if (isLoading) return <Loading />;
          if (isError) return <Error />;
          const hasTag = isSuccess && data.tags.length > 0;
          if (!hasTag) return <NoTags />;
          if (hasTag && !etcTag) return <ETCTagError />;
          <MultiTagSelect defaultSelectedOptions={selectedOptions} options={options} {...register(SUBJECT)} />;
        })()}
      </MetaBox.Content>
    </MetaBox>
  );
};

const Loading = () => <div>Loading...</div>;

const Error = () => <div>Error!</div>;

const NoTags = () => <div>선택 가능한 주제(태그)가 없습니다</div>;

const ETCTagError = () => <div>%ERROR%</div>;

export default Subject;
