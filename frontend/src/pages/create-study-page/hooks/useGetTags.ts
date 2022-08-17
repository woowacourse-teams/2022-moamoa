import type { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { GetTagsResponseData } from '@custom-types';

import { getTags } from '@api';

export const useGetTags = () => {
  return useQuery<GetTagsResponseData, AxiosError>('filters', getTags);
};
