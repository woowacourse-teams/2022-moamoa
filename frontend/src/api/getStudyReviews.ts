import type { GetReviewRequestParams, GetReviewResponseData } from '@custom-types';

import { axiosInstance } from '@api';

const getStudyReviews = async ({ studyId, size }: GetReviewRequestParams) => {
  const url = size ? `/api/studies/${studyId}/reviews?size=${size}` : `/api/studies/${studyId}/reviews`;
  const response = await axiosInstance.get<GetReviewResponseData>(url);
  return response.data;
};

export default getStudyReviews;
