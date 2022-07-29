import * as S from '@create-study-page/components/category/Category.style';
import MetaBox from '@create-study-page/components/meta-box/MetaBox';

import { css } from '@emotion/react';

import type { Tag } from '@custom-types/index';

import { useFormContext } from '@hooks/useForm';

import useFetchTagList from '@pages/create-study-page/hooks/useFetchTagList';

type CategoryProps = {
  className?: string;
};

const getClassifiedTags = (tags: Array<Tag>) => {
  const generations = tags.filter(({ category }) => category.name === 'generation'); // TODO: 기수 정렬하기. 서버에서 order를 줘도 되겠다
  const areas = tags.filter(({ category }) => category.name === 'area');
  const areaFE = areas.find(({ name }) => name.toLowerCase() === 'fe');
  const areaBE = areas.find(({ name }) => name.toLowerCase() === 'be');

  return {
    generations,
    areaFE,
    areaBE,
  };
};

const Category = ({ className }: CategoryProps) => {
  const { register } = useFormContext();
  const { data, isLoading, isError, isSuccess } = useFetchTagList();

  const renderContent = () => {
    if (isLoading) return <div>loading...</div>;

    if (!isSuccess || isError) return <div>Error!</div>;

    const { tags } = data;

    const { areaFE, areaBE, generations } = getClassifiedTags(tags);

    if (!areaFE || !areaBE) return <div>FE/BE영역에 오류가 있습니다. 서버 관리자에게 문의해주세요</div>;

    return (
      <>
        <div
          css={css`
            margin-bottom: 6px;
          `}
        >
          <label
            css={css`
              margin-right: 10px;
            `}
            htmlFor="generation"
          >
            기수 :
          </label>
          <select id="generation" {...register('generation')}>
            {generations.map(({ id, name }) => (
              <option key={id} value={id}>
                {name}
              </option>
            ))}
          </select>
        </div>
        <div
          css={css`
            display: flex;
          `}
        >
          <span
            css={css`
              margin-right: 10px;
            `}
          >
            영역 :
          </span>
          {}
          <div
            css={css`
              display: flex;
              margin-right: 8px;
            `}
          >
            <input
              css={css`
                margin-right: 4px;
              `}
              type="checkbox"
              id="area-fe"
              data-tagid={areaFE.id}
              {...register('area-fe')}
            />
            <label htmlFor="area-fe">FE</label>
          </div>
          <div
            css={css`
              display: flex;
              margin-right: 8px;
            `}
          >
            <input
              css={css`
                margin-right: 4px;
              `}
              type="checkbox"
              id="area-be"
              data-tagid={areaBE.id}
              {...register('area-be')}
            />
            <label htmlFor="area-be">BE</label>
          </div>
        </div>
      </>
    );
  };

  return (
    <S.Category className={className}>
      <MetaBox>
        <MetaBox.Title>스터디 분류</MetaBox.Title>
        <MetaBox.Content>{renderContent()}</MetaBox.Content>
      </MetaBox>
    </S.Category>
  );
};

export default Category;
