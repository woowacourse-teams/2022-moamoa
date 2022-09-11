import tw from '@utils/tw';

import type { StudyDetail, Tag } from '@custom-types';

import { useGetTags } from '@api/tags';

import { useFormContext } from '@hooks/useForm';

import Checkbox from '@design/components/checkbox/Checkbox';
import Flex from '@design/components/flex/Flex';
import Label from '@design/components/label/Label';
import MetaBox from '@design/components/meta-box/MetaBox';
import Select from '@design/components/select/Select';

export type CategoryProps = {
  originalGeneration?: Tag;
  originalAreas?: StudyDetail['tags'];
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
        <Flex gap="8px" alignItems="center">
          <Label htmlFor="generation">기수 :</Label>
          <div css={tw`flex-grow`}>
            <Select id="generation" defaultValue={originalGeneration?.id} fluid {...register('generation')}>
              <option>선택 안함</option>
              {generations.map(({ id, name }) => (
                <option key={id} value={id}>
                  {name}
                </option>
              ))}
            </Select>
          </div>
        </Flex>
        <Flex gap="8px">
          <Label>영역 :</Label>
          <Checkbox
            id="area-fe"
            dataTagId={areaFE.id}
            defaultChecked={originalAreas?.some(tag => tag.id === areaFE.id)}
            {...register('area-fe')}
          >
            FE
          </Checkbox>
          <Checkbox
            id="area-be"
            dataTagId={areaBE.id}
            defaultChecked={originalAreas?.some(tag => tag.id === areaBE.id)}
            {...register('area-be')}
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
