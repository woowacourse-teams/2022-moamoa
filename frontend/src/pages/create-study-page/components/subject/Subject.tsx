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

const Subject: React.FC<SubjectProps> = ({ originalSubjects }) => {
  const { register } = useFormContext();
  const { data, isLoading, isError } = useGetTags();

  const originalOptions = originalSubjects?.map(({ id, description }) => ({
    label: description,
    value: id,
  }));

  const render = () => {
    if (isLoading) return <div>loading...</div>;

    if (isError) return <div>Error!</div>;

    if (data?.tags) {
      const { tags } = data;
      const subjects = tags.filter(({ category }) => category.name === SUBJECT);
      const etcTag = subjects.find(tag => tag.name === 'Etc');
      if (!etcTag) {
        throw new Error('기타 태그의 name이 변경되었습니다');
      }
      const selectedOptions = originalOptions ? originalOptions : [{ value: etcTag.id, label: etcTag.description }];

      const options = subjects.reduce((acc, { id, description }) => {
        acc.push({
          label: description,
          value: id,
        });
        return acc;
      }, [] as MultiTagSelectProps['options']);

      return <MultiTagSelect defaultSelectedOptions={selectedOptions} options={options} {...register(SUBJECT)} />;
    }
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
