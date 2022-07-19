import { StudyDetail } from '@custom-types/index';

import axiosInstance from '@api/axiosInstance';

const getStudyDetail = async (studyId: number): Promise<{ study: StudyDetail }> => {
  const response = await axiosInstance.get<{ study: StudyDetail }>(`/api/studies/${studyId}`);
  return response.data;
};

export default getStudyDetail;
