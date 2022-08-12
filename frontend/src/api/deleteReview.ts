import type { AxiosResponse } from 'axios';

import type { DeleteReviewRequestBody } from '@custom-types';

import { axiosInstance } from '@api';

const deleteReview = async ({ studyId, reviewId }: DeleteReviewRequestBody) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, DeleteReviewRequestBody>(
    `/api/studies/${studyId}/reviews/${reviewId}`,
  );
  return response.data;
};

export default deleteReview;
