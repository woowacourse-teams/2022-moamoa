import type { GetStudyDetailRequestParams, GetStudyDetailResponseData } from '@custom-types';

import { axiosInstance } from '@api';

const getStudyDetail = async ({ studyId }: GetStudyDetailRequestParams) => {
  const response = await axiosInstance.get<GetStudyDetailResponseData>(`/api/studies/${studyId}`);
  return response.data;
};

export default getStudyDetail;
