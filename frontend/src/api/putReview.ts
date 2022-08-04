import type { AxiosResponse } from 'axios';

import type { EmptyObject, PutReviewRequestBody, PutReviewRequestVariables } from '@custom-types';

import { axiosInstance } from '@api';

const putReview = async ({ studyId, reviewId, content }: PutReviewRequestVariables) => {
  const response = await axiosInstance.put<EmptyObject, AxiosResponse<EmptyObject>, PutReviewRequestBody>(
    `/api/studies/${studyId}/reviews/${reviewId}`,
    {
      content,
    },
  );
  return response.data;
};

export default putReview;
