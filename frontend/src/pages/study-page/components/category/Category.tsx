import type { StudyDetail, Tag } from '@custom-types';

import { useGetTags } from '@api/tags';

import { type UseFormRegister, useFormContext } from '@hooks/useForm';

import Checkbox from '@components/checkbox/Checkbox';
import Flex from '@components/flex/Flex';
import Label from '@components/label/Label';
import MetaBox from '@components/meta-box/MetaBox';
import Select from '@components/select/Select';

export type CategoryProps = {
  originalGeneration?: Tag;
  originalAreas?: StudyDetail['tags'];
};

const GENERATION = 'generation';
const AREA_FE = 'area-fe';
const AREA_BE = 'area-be';

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
  const { register } = useFormContext();
  const { data, isLoading, isError, isSuccess } = useGetTags();

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
  const hasAllArea = !!(areaFE && areaBE);

  return (
    <MetaBox>
      <MetaBox.Title>스터디 분류</MetaBox.Title>
      <MetaBox.Content>
        {isLoading && <Loading />}
        {isError && <Error />}
        {isSuccess && !isError && !hasAllArea && <EmptyAreaError />}
        {isSuccess && !isError && hasAllArea && generations && (
          <GenerationSelect generations={generations} originalGeneration={originalGeneration} register={register} />
        )}
        {isSuccess && !isError && areaFE && areaBE && (
          <AreaCheckboxList areaFE={areaFE} areaBE={areaBE} originalAreas={originalAreas} register={register} />
        )}
      </MetaBox.Content>
    </MetaBox>
  );
};

const Loading = () => <div>Loading...</div>;

const Error = () => <div>Error...</div>;

const EmptyAreaError = () => <div>FE/BE영역에 오류가 있습니다. 서버 관리자에게 문의해주세요</div>;

type GenerationSelectProps = {
  generations: Array<Tag>;
  originalGeneration: CategoryProps['originalGeneration'];
  register: UseFormRegister;
};
const GenerationSelect: React.FC<GenerationSelectProps> = ({ generations, originalGeneration, register }) => (
  <Flex columnGap="8px" alignItems="center">
    <Label htmlFor={GENERATION}>기수 :</Label>
    <Flex.Item flexGrow={1}>
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

type AreaCheckboxListProps = {
  areaFE: Tag;
  areaBE: Tag;
  originalAreas: CategoryProps['originalAreas'];
  register: UseFormRegister;
};
const AreaCheckboxList: React.FC<AreaCheckboxListProps> = ({ areaFE, areaBE, originalAreas, register }) => (
  <Flex columnGap="8px">
    <Label>영역 :</Label>
    <FEAreaCheckbox area={areaFE} originalAreas={originalAreas} register={register} />
    <BEAreaCheckbox area={areaBE} originalAreas={originalAreas} register={register} />
  </Flex>
);

type FEAreaCheckboxProps = {
  area: Tag;
  originalAreas: CategoryProps['originalAreas'];
  register: UseFormRegister;
};
const FEAreaCheckbox: React.FC<FEAreaCheckboxProps> = ({ area, originalAreas, register }) => (
  <Checkbox
    id={AREA_FE}
    dataTagId={area.id}
    defaultChecked={originalAreas?.some(tag => tag.id === area.id)}
    {...register(AREA_FE)}
  >
    FE
  </Checkbox>
);

type BEAreaCheckboxProps = {
  area: Tag;
  originalAreas: CategoryProps['originalAreas'];
  register: UseFormRegister;
};
const BEAreaCheckbox: React.FC<BEAreaCheckboxProps> = ({ area, originalAreas, register }) => (
  <Checkbox
    id={AREA_BE}
    dataTagId={area.id}
    defaultChecked={originalAreas?.some(tag => tag.id === area.id)}
    {...register(AREA_BE)}
  >
    BE
  </Checkbox>
);

export default Category;
