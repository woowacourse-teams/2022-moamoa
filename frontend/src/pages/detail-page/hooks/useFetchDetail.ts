import { useQuery } from 'react-query';

import { QK_FETCH_STUDY_DETAIL } from '@constants';

import { StudyDetail } from '@custom-types';

import getStudyDetail from '@api/getStudyDetail';

const useFetchDetail = (studyId: number) => {
  return useQuery<StudyDetail, Error>([QK_FETCH_STUDY_DETAIL, studyId], () => getStudyDetail(studyId));
};

export default useFetchDetail;
