import tw from '@utils/tw';

import type { StudyDetail } from '@custom-types';

import { useGetTags } from '@api/tags';

import { useFormContext } from '@hooks/useForm';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import MultiTagSelect, {
  type MultiTagSelectProps,
} from '@create-study-page/components/multi-tag-select/MultiTagSelect';
import * as S from '@create-study-page/components/subject/Subject.style';

export type SubjectProps = {
  className?: string;
  originalSubjects?: StudyDetail['tags'];
};

const Subject: React.FC<SubjectProps> = ({ className, originalSubjects }) => {
  const { register } = useFormContext();
  const { data, isLoading, isError } = useGetTags();

  const render = () => {
    if (isLoading) return <div>loading...</div>;

    if (isError) return <div>Error!</div>;

    if (data?.tags) {
      const { tags } = data;
      const subjects = tags.filter(({ category }) => category.name === 'subject');

      const options = subjects.reduce((acc, { id, description }) => {
        acc.push({
          label: description,
          value: id,
        });
        return acc;
      }, [] as MultiTagSelectProps['options']);

      return <MultiTagSelect options={options} />;
    }
  };

  return (
    <S.Subject className={className}>
      <MetaBox>
        <MetaBox.Title>
          <label htmlFor="subject-list">주제</label>
        </MetaBox.Title>
        <MetaBox.Content>{render()}</MetaBox.Content>
      </MetaBox>
    </S.Subject>
  );
};

export default Subject;
