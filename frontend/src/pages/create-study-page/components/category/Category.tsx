import tw from '@utils/tw';

import type { StudyDetail, Tag } from '@custom-types';

import { useGetTags } from '@api/tags';

import { useFormContext } from '@hooks/useForm';

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

const Category: React.FC<CategoryProps> = ({ originalGeneration, originalAreas }) => {
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
        <Flex columnGap="8px" alignItems="center">
          <Label htmlFor={GENERATION}>기수 :</Label>
          <div css={tw`flex-grow`}>
            <Select id={GENERATION} defaultValue={originalGeneration?.id} fluid {...register(GENERATION)}>
              <option>선택 안함</option>
              {generations.map(({ id, name }) => (
                <option key={id} value={id}>
                  {name}
                </option>
              ))}
            </Select>
          </div>
        </Flex>
        <Flex columnGap="8px">
          <Label>영역 :</Label>
          <Checkbox
            id={AREA_FE}
            dataTagId={areaFE.id}
            defaultChecked={originalAreas?.some(tag => tag.id === areaFE.id)}
            {...register(AREA_FE)}
          >
            FE
          </Checkbox>
          <Checkbox
            id={AREA_BE}
            dataTagId={areaBE.id}
            defaultChecked={originalAreas?.some(tag => tag.id === areaBE.id)}
            {...register(AREA_BE)}
          >
            BE
          </Checkbox>
        </Flex>
      </>
    );
  };

  return (
    <MetaBox>
      <MetaBox.Title>스터디 분류</MetaBox.Title>
      <MetaBox.Content>{renderContent()}</MetaBox.Content>
    </MetaBox>
  );
};

export default Category;
