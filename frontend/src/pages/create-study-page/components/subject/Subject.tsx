import { css } from '@emotion/react';

import { useFormContext } from '@hooks/useForm';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import * as S from '@create-study-page/components/subject/Subject.style';
import useGetTagList from '@create-study-page/hooks/useGetTagList';

type SubjectProps = {
  className?: string;
};

const Subject = ({ className }: SubjectProps) => {
  const { register } = useFormContext();
  const { data, isLoading, isError } = useGetTagList();

  const render = () => {
    if (isLoading) return <div>loading...</div>;

    if (isError) return <div>Error!</div>;

    if (data?.tags) {
      const { tags } = data;
      const subjects = tags.filter(({ category }) => category.name === 'subject');

      return (
        <S.Select
          id="subject-list"
          css={css`
            width: 100%;
          `}
          {...register('subject')}
        >
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
