import { useQuery } from 'react-query';

import { QK_FETCH_STUDY_DETAIL } from '@constants';

import type { GetStudyDetailResponseData } from '@custom-types';

import { getStudyDetail } from '@api';

const useGetDetail = (studyId: number) => {
  return useQuery<GetStudyDetailResponseData, Error>([QK_FETCH_STUDY_DETAIL, studyId], () =>
    getStudyDetail({ studyId }),
  );
};

export default useGetDetail;
