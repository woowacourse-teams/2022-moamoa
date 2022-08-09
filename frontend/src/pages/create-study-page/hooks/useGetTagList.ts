import type { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { GetTagListResponseData } from '@custom-types';

import { getTagList } from '@api';

const useGetTagList = () => {
  return useQuery<GetTagListResponseData, AxiosError>('filters', getTagList);
};

export default useGetTagList;
