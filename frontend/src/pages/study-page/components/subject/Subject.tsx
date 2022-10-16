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

  return (
    <MetaBox>
      <MetaBox.Title>
        <Label htmlFor={SUBJECT}>주제</Label>
      </MetaBox.Title>
      <MetaBox.Content>
        {(() => {
          if (isLoading) return <Loading />;
          if (isError) return <Error />;

          if (isSuccess) {
            const subjects = data.tags.filter(({ category }) => category.name === SUBJECT);
            if (data.tags.length === 0) return <NoTags />;

            const etcTag = subjects.find(tag => tag.name === 'Etc');
            if (!etcTag) return <ETCTagError />;

            const originalOptions = originalSubjects ? subjectsToOptions(originalSubjects) : null;
            let selectedOptions: Array<Option> = [];
            if (originalOptions) {
              selectedOptions = originalOptions;
            } else if (etcTag) {
              selectedOptions = [{ value: etcTag.id, label: etcTag.description }];
            }
            const options = subjects ? subjectsToOptions(subjects) : [];
            return <MultiTagSelect defaultSelectedOptions={selectedOptions} options={options} {...register(SUBJECT)} />;
          }
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
