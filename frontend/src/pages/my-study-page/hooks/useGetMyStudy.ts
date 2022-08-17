import type { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { GetMyStudiesResponseData } from '@custom-types';

import { getMyStudies } from '@api';

const useGetMyStudy = () => {
  return useQuery<GetMyStudiesResponseData, AxiosError>('my-studies', getMyStudies);
};

export default useGetMyStudy;
