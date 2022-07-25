import { QK_FETCH_STUDY_DETAIL } from '@constants';
import { useQuery } from 'react-query';

import { StudyDetail } from '@custom-types/index';

import getStudyDetail from '@api/getStudyDetail';

const useFetchDetail = (studyId: number) => {
  return useQuery<StudyDetail, Error>([QK_FETCH_STUDY_DETAIL, studyId], () => getStudyDetail(studyId));
};

export default useFetchDetail;
