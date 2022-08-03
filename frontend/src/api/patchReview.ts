import type { AxiosResponse } from 'axios';

import type { EmptyObject, PatchReviewRequestBody, PatchReviewRequestVariables } from '@custom-types';

import { axiosInstance } from '@api';

const patchReview = async ({ studyId, reviewId, content }: PatchReviewRequestVariables) => {
  const response = await axiosInstance.patch<EmptyObject, AxiosResponse<EmptyObject>, PatchReviewRequestBody>(
    `/api/studies/${studyId}/reviews/${reviewId}`,
    {
      content,
    },
  );
  return response.data;
};

export default patchReview;