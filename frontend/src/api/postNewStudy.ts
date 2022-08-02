import type { AxiosResponse } from 'axios';

import type { EmptyObject, PostNewStudyRequestBody } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

const postNewStudy = async (data: PostNewStudyRequestBody) => {
  const response = await axiosInstance.post<EmptyObject, AxiosResponse<EmptyObject>, PostNewStudyRequestBody>(
    `/api/studies`,
    data,
  );
  return response.data;
};

export default postNewStudy;
