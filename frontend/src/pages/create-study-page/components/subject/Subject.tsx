import tw from '@utils/tw';

import type { StudyDetail } from '@custom-types';

import { useGetTags } from '@api/tags';

import { useFormContext } from '@hooks/useForm';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';
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
      const etcTagId = subjects.find(tag => tag.name === 'Etc');

      return (
        <S.Select
          id="subject-list"
          defaultValue={(originalSubjects && originalSubjects[0].id) || etcTagId?.id}
          css={tw`w-full`}
          {...register('subject')}
        >
          {subjects.map(({ id, description }) => (
            <option key={id} value={id}>
              {description}
            </option>
          ))}
        </S.Select>
      );
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
