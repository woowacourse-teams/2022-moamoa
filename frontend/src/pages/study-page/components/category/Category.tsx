import { CATEGORY_NAME } from '@constants';

import type { StudyDetail, Tag } from '@custom-types';

import { useGetTags } from '@api/tags';

import { useFormContext } from '@hooks/useForm';

import Checkbox from '@shared/checkbox/Checkbox';
import Flex from '@shared/flex/Flex';
import Label from '@shared/label/Label';
import MetaBox from '@shared/meta-box/MetaBox';
import Select from '@shared/select/Select';

export type CategoryProps = {
  originalGeneration?: Tag;
  originalAreas?: StudyDetail['tags'];
};

const GENERATION = CATEGORY_NAME.GENERATION;
const AREA_FE = CATEGORY_NAME.AREA_FE;
const AREA_BE = CATEGORY_NAME.AREA_BE;

const getClassifiedTags = (tags: Array<Tag>) => {
  const generations = tags.filter(({ category }) => category.name === GENERATION); // TODO: 기수 정렬하기. 서버에서 order를 줘도 되겠다
  const areas = tags.filter(({ category }) => category.name === 'area');
  const areaFE = areas.find(({ name }) => name.toLowerCase() === 'fe');
  const areaBE = areas.find(({ name }) => name.toLowerCase() === 'be');

  return {
    generations,
    areaFE,
    areaBE,
  };
};

const checkHasTags = (tags: unknown): tags is Array<Tag> => {
  return !!tags;
};

const Category: React.FC<CategoryProps> = ({ originalGeneration, originalAreas }) => {
  const { data, isLoading, isError } = useGetTags();

  const tags = data?.tags;
  let areaFE: Tag | undefined;
  let areaBE: Tag | undefined;
  let generations: Array<Tag> | undefined;
  if (checkHasTags(tags)) {
    const { areaFE: _areaFE, areaBE: _areaBE, generations: _generations } = getClassifiedTags(tags);
    areaFE = _areaFE;
    areaBE = _areaBE;
    generations = _generations;
  }

  return (
    <MetaBox>
      <MetaBox.Title>스터디 분류</MetaBox.Title>
      <MetaBox.Content>
        {(() => {
          if (isLoading) return <Loading />;
          if (isError) return <Error />;
          if (!areaFE || !areaBE) return <EmptyAreaError />;
          if (!generations) return <EmptyGenerationError />;
          return (
            <>
              <GenerationSelect generations={generations} originalGeneration={originalGeneration} />
              <AreaCheckboxList areaFE={areaFE} areaBE={areaBE} originalAreas={originalAreas} />
            </>
          );
        })()}
      </MetaBox.Content>
    </MetaBox>
  );
};

export default Category;

const Loading = () => <div>Loading...</div>;

const Error = () => <div>Error...</div>;

const EmptyAreaError = () => <div>FE/BE영역에 오류가 있습니다. 서버 관리자에게 문의해주세요</div>;

const EmptyGenerationError = () => <div>기수 영역에 오류가 있습니다. 서버 관리자에게 문의해주세요</div>;

type GenerationSelectProps = {
  generations: Array<Tag>;
  originalGeneration: CategoryProps['originalGeneration'];
};
const GenerationSelect: React.FC<GenerationSelectProps> = ({ generations, originalGeneration }) => {
  const { register } = useFormContext();

  return (
    <Flex columnGap="8px" alignItems="center">
      <Label htmlFor={GENERATION}>기수 :</Label>
      <Flex.Item>
        <Select id={GENERATION} defaultValue={originalGeneration?.id} fluid {...register(GENERATION)}>
          <option>선택 안함</option>
          {generations.map(({ id, name }) => (
            <option key={id} value={id}>
              {name}
            </option>
          ))}
        </Select>
      </Flex.Item>
    </Flex>
  );
};

type AreaCheckboxListProps = {
  areaFE: Tag;
  areaBE: Tag;
  originalAreas: CategoryProps['originalAreas'];
};
const AreaCheckboxList: React.FC<AreaCheckboxListProps> = ({ areaFE, areaBE, originalAreas }) => (
  <Flex columnGap="8px">
    <Label>영역 :</Label>
    <FEAreaCheckbox area={areaFE} originalAreas={originalAreas} />
    <BEAreaCheckbox area={areaBE} originalAreas={originalAreas} />
  </Flex>
);

type FEAreaCheckboxProps = {
  area: Tag;
  originalAreas: CategoryProps['originalAreas'];
};
const FEAreaCheckbox: React.FC<FEAreaCheckboxProps> = ({ area, originalAreas }) => {
  const { register } = useFormContext();

  return (
    <Checkbox
      id={AREA_FE}
      dataTagId={area.id}
      defaultChecked={originalAreas?.some(tag => tag.id === area.id)}
      {...register(AREA_FE)}
    >
      FE
    </Checkbox>
  );
};

type BEAreaCheckboxProps = {
  area: Tag;
  originalAreas: CategoryProps['originalAreas'];
};
const BEAreaCheckbox: React.FC<BEAreaCheckboxProps> = ({ area, originalAreas }) => {
  const { register } = useFormContext();

  return (
    <Checkbox
      id={AREA_BE}
      dataTagId={area.id}
      defaultChecked={originalAreas?.some(tag => tag.id === area.id)}
      {...register(AREA_BE)}
    >
      BE
    </Checkbox>
  );
};
