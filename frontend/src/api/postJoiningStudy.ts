import type { AxiosResponse } from 'axios';

import type { PostJoiningStudyRequestParams } from '@custom-types';

import { axiosInstance } from '@api';

const postJoiningStudy = async ({ studyId }: PostJoiningStudyRequestParams) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, PostJoiningStudyRequestParams>(
    `/api/studies/${studyId}`,
  );
  return response.data;
};

export default postJoiningStudy;
