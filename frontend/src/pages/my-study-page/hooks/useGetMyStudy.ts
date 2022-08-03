import { useQuery } from 'react-query';

import type { GetMyStudyResponseData } from '@custom-types';

import { getMyStudyList } from '@api';

const useGetMyStudy = () => {
  return useQuery<GetMyStudyResponseData, Error>('my-studies', getMyStudyList);
};

export default useGetMyStudy;
