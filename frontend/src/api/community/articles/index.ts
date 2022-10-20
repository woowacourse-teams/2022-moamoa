import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { CommunityArticle, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkCommunityArticles } from '@api/community/articles/typeChecker';

export type ApiCommunityArticles = {
  get: {
    responseData: {
      articles: Array<Omit<CommunityArticle, 'content'>>;
      currentPage: number;
      lastPage: number;
      totalCount: number;
    };
    params: {
      studyId: StudyId;
      page?: Page;
      size?: Size;
    };
    variables: ApiCommunityArticles['get']['params'];
  };
};

const getCommunityArticles = async ({ studyId, page = 1, size = 8 }: ApiCommunityArticles['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiCommunityArticles['get']['responseData']>(
    `/api/studies/${studyId}/community/articles?page=${page - 1}&size=${size}`,
  );

  return checkCommunityArticles(response.data);
};

// articles
export const useGetCommunityArticles = ({ studyId, page }: ApiCommunityArticles['get']['variables']) => {
  return useQuery<ApiCommunityArticles['get']['responseData'], AxiosError>(
    ['get-community-articles', studyId, page],
    () => getCommunityArticles({ studyId, page }),
  );
};
