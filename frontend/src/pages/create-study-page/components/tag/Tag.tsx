import { css } from '@emotion/react';

import { useFormContext } from '@hooks/useForm';

import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import * as S from '@create-study-page/components/tag/Tag.style';
import useGetTagList from '@create-study-page/hooks/useGetTagList';

type TagProps = {
  className?: string;
};

const Tag = ({ className }: TagProps) => {
  const { register } = useFormContext();
  const { data, isLoading, isError } = useGetTagList();

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
          <option>선택 안함</option>
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
          <label htmlFor="tag-list">주제</label>
        </MetaBox.Title>
        <MetaBox.Content>{render()}</MetaBox.Content>
      </MetaBox>
    </S.Tag>
  );
};

export default Tag;
