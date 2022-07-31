import { StudyDetail } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

const getStudyDetail = async (studyId: number): Promise<StudyDetail> => {
  const response = await axiosInstance.get<StudyDetail>(`/api/studies/${studyId}`);
  return response.data;
};

export default getStudyDetail;
