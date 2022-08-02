import { useQuery } from 'react-query';

import type { GetTagListResponseData } from '@custom-types';

import { getTagList } from '@api/getTagList';

const useFetchTagList = () => {
  return useQuery<GetTagListResponseData, Error>('filters', getTagList);
};

export default useFetchTagList;
