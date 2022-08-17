import tw from '@utils/tw';

import type { Tag } from '@custom-types';

import { useFormContext } from '@hooks/useForm';

import Checkbox from '@components/checkbox/Checkbox';

import * as S from '@create-study-page/components/category/Category.style';
import MetaBox from '@create-study-page/components/meta-box/MetaBox';
import { useGetTags } from '@create-study-page/hooks/useGetTags';

export type CategoryProps = {
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
  const { data, isLoading, isError, isSuccess } = useGetTags();

  const renderContent = () => {
    if (isLoading) return <div>loading...</div>;

    if (!isSuccess || isError) return <div>Error!</div>;

    const { tags } = data;

    const { areaFE, areaBE, generations } = getClassifiedTags(tags);

    if (!areaFE || !areaBE) return <div>FE/BE영역에 오류가 있습니다. 서버 관리자에게 문의해주세요</div>;

    return (
      <>
        <S.Generation>
          <S.Label htmlFor="generation">기수 :</S.Label>
          <S.Select id="generation" {...register('generation')}>
            <option>선택 안함</option>
            {generations.map(({ id, name }) => (
              <option key={id} value={id}>
                {name}
              </option>
            ))}
          </S.Select>
        </S.Generation>
        <S.Area>
          <S.Label>영역 :</S.Label>
          <S.AreaCheckboxContainer>
            <Checkbox css={tw`mr-4`} type="checkbox" id="area-fe" data-tagid={areaFE.id} {...register('area-fe')} />
            <label htmlFor="area-fe">FE</label>
          </S.AreaCheckboxContainer>
          <S.AreaCheckboxContainer>
            <Checkbox css={tw`mr-4`} type="checkbox" id="area-be" data-tagid={areaBE.id} {...register('area-be')} />
            <label htmlFor="area-be">BE</label>
          </S.AreaCheckboxContainer>
        </S.Area>
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
