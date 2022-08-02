import { useQuery } from 'react-query';

import type { GetTagListResponseData } from '@custom-types';

import { getTagList } from '@api';

const useFetchTagList = () => {
  return useQuery<GetTagListResponseData, Error>('filters', getTagList);
};

export default useFetchTagList;
