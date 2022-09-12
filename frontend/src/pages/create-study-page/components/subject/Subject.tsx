import type { StudyDetail } from '@custom-types';

import { useGetTags } from '@api/tags';

import { useFormContext } from '@hooks/useForm';

import Label from '@design/components/label/Label';
import MetaBox from '@design/components/meta-box/MetaBox';
import Select from '@design/components/select/Select';

export type SubjectProps = {
  originalSubjects?: StudyDetail['tags'];
};

const SUBJECT = 'subject';

const Subject: React.FC<SubjectProps> = ({ originalSubjects }) => {
  const { register } = useFormContext();
  const { data, isLoading, isError } = useGetTags();

  const render = () => {
    if (isLoading) return <div>loading...</div>;

    if (isError) return <div>Error!</div>;

    if (data?.tags) {
      const { tags } = data;
      const subjects = tags.filter(({ category }) => category.name === SUBJECT);
      const etcTagId = subjects.find(tag => tag.name === 'Etc');

      return (
        <Select
          id={SUBJECT}
          defaultValue={(originalSubjects && originalSubjects[0].id) || etcTagId?.id}
          fluid
          {...register(SUBJECT)}
        >
          {subjects.map(({ id, description }) => (
            <option key={id} value={id}>
              {description}
            </option>
          ))}
        </Select>
      );
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
