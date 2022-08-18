import type { GetCommunityArticleResponseData } from '@custom-types';

import { axiosInstance } from '@api';

type GetCommunityArticlesArgs = {
  studyId: number;
  page?: number;
  size?: number;
};

const getCommunityArticles = async ({ studyId, page = 1, size = 8 }: GetCommunityArticlesArgs) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<GetCommunityArticleResponseData>(
    `/api/studies/${studyId}/community/articles?page=${page - 1}&size=${size}`,
  );
  const { totalCount, currentPage, lastPage } = response.data;

  response.data = {
    ...response.data,
    totalCount: Number(totalCount),
    currentPage: Number(currentPage) + 1, // page를 하나 늘려준다 서버에서 0으로 오기 때문이다
    lastPage: Number(lastPage),
  };

  return response.data;
};

export default getCommunityArticles;
