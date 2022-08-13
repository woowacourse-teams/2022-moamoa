import type { GetMyStudyResponseData } from '@custom-types';

import { axiosInstance } from '@api';

type GetCommunityArticlesArgs = {
  studyId: number;
  page?: number;
  size?: number;
};

const getCommunityArticles = async ({ studyId, page = 0, size = 10 }: GetCommunityArticlesArgs) => {
  const response = await axiosInstance.get<GetMyStudyResponseData>(
    `/api/studies/${studyId}/community/articles?page=${page}&size=${size}`,
  );
  return response.data;
};

export default getCommunityArticles;
