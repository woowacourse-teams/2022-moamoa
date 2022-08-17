import tw from '@utils/tw';

import { useGetTags } from '@api/tags';

import { useFormContext } from '@hooks/useForm';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import * as S from '@create-study-page/components/subject/Subject.style';

type SubjectProps = {
  className?: string;
};

const Subject = ({ className }: SubjectProps) => {
  const { register } = useFormContext();
  const { data, isLoading, isError } = useGetTags();

  const render = () => {
    if (isLoading) return <div>loading...</div>;

    if (isError) return <div>Error!</div>;

    if (data?.tags) {
      const { tags } = data;
      const subjects = tags.filter(({ category }) => category.name === 'subject');

      return (
        <S.Select id="subject-list" css={tw`w-full`} {...register('subject')}>
          {subjects.map(({ id, name, description }) => (
            <option selected={name === 'Etc'} key={id} value={id}>
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
