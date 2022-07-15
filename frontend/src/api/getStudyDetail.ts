import { StudyDetail } from '@custom-types/index';

import axiosInstance from './axiosInstance';

const getStudyDetail = async (studyId: string): Promise<{ study: StudyDetail }> => {
  const response = await axiosInstance.get<{ study: StudyDetail }>(`/api/study/?study-id=${studyId}`);
  return response.data;
};

export default getStudyDetail;
