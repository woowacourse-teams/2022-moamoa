import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import * as S from '@create-study-page/components/tag/Tag.style';

import { css } from '@emotion/react';

import { useFormContext } from '@hooks/useForm';

import useFetchTagList from '@pages/create-study-page/hooks/useFetchTagList';

type TagProps = {
  className?: string;
};

const Tag = ({ className }: TagProps) => {
  const { register } = useFormContext();
  const { data, isLoading, isError } = useFetchTagList();

  const render = () => {
    if (isLoading) return <div>loading...</div>;

    if (isError) return <div>Error!</div>;

    if (data?.tags) {
      const { tags } = data;
      const subjects = tags.filter(({ category }) => category.name === 'subject');

      return (
        <select
          id="tag-list"
          css={css`
            width: 100%;
          `}
          {...register('subject')}
        >
          {subjects.map(({ id, name }) => (
            <option key={id} value={id}>
              {name}
            </option>
          ))}
        </select>
      );
    }
  };

  return (
    <S.Tag className={className}>
      <MetaBox>
        <MetaBox.Title>
          <label htmlFor="tag-list">태그</label>
        </MetaBox.Title>
        <MetaBox.Content>{render()}</MetaBox.Content>
      </MetaBox>
    </S.Tag>
  );
};

export default Tag;
