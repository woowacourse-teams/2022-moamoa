import type { AxiosResponse } from 'axios';

import { PostReviewRequestBody, PostReviewRequestVariables } from '@custom-types';

import { axiosInstance } from '@api';

const postReview = async ({ studyId, content }: PostReviewRequestVariables) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, PostReviewRequestBody>(
    `/api/studies/${studyId}/reviews`,
    {
      content,
    },
  );
  return response.data;
};

export default postReview;
