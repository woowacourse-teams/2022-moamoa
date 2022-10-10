import { useMemo } from 'react';

import { STUDY_STATUS } from '@constants';

import type { MyStudy, StudyStatus } from '@custom-types';

import { useGetMyStudies } from '@api/my-studies';

const filterStudiesByStatus = (studies: Array<MyStudy>, status: StudyStatus) => {
  return studies.filter(({ studyStatus }) => studyStatus === status);
};

export const useMyStudyPage = () => {
  const myStudyQueryResult = useGetMyStudies();

  const filteredStudies: Record<string, Array<MyStudy>> = useMemo(() => {
    const studies = myStudyQueryResult.data?.studies ?? [];
    return {
      prepare: filterStudiesByStatus(studies, STUDY_STATUS.PREPARE),
      inProgress: filterStudiesByStatus(studies, STUDY_STATUS.IN_PROGRESS),
      done: filterStudiesByStatus(studies, STUDY_STATUS.DONE),
    };
  }, [myStudyQueryResult.data]);

  return {
    myStudyQueryResult,
    studies: filteredStudies,
  };
};
