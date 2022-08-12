import type { AxiosResponse } from 'axios';

import type { PostNewStudyRequestBody } from '@custom-types';

import { axiosInstance } from '@api';

const postNewStudy = async (data: PostNewStudyRequestBody) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, PostNewStudyRequestBody>(`/api/studies`, data);
  return response.data;
};

export default postNewStudy;
