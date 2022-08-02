import type { AxiosResponse } from 'axios';

import type { EmptyObject, PostJoiningStudyRequestParams } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

const postJoiningStudy = async ({ studyId }: PostJoiningStudyRequestParams) => {
  const response = await axiosInstance.post<EmptyObject, AxiosResponse<EmptyObject>, PostJoiningStudyRequestParams>(
    `/api/studies/${studyId}`,
  );
  return response.data;
};

export default postJoiningStudy;
