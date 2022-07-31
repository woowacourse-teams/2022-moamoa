import { useQuery } from 'react-query';

import { TagListQueryData } from '@custom-types';

import { getTagList } from '@api/getTagList';

const useFetchTagList = () => {
  return useQuery<TagListQueryData, Error>('filters', getTagList);
};

export default useFetchTagList;
