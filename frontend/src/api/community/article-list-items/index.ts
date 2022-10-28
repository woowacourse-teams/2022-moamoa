import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { CommunityArticleDetail, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkCommunityArticleListItems } from '@api/community/article-list-items/typeChecker';

export type ApiCommunityArticleListItems = {
  get: {
    responseData: {
      articles: Array<Omit<CommunityArticleDetail, 'content'>>;
      currentPage: number;
      lastPage: number;
      totalCount: number;
    };
    params: {
      studyId: StudyId;
      page?: Page;
      size?: Size;
    };
    variables: ApiCommunityArticleListItems['get']['params'];
  };
};

const getCommunityArticleListItems = async ({
  studyId,
  page = 1,
  size = 8,
}: ApiCommunityArticleListItems['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiCommunityArticleListItems['get']['responseData']>(
    `/api/studies/${studyId}/community/articles?page=${page - 1}&size=${size}`,
  );

  return checkCommunityArticleListItems(response.data);
};

// articles
export const useGetCommunityArticleListItems = ({
  studyId,
  page,
}: ApiCommunityArticleListItems['get']['variables']) => {
  return useQuery<ApiCommunityArticleListItems['get']['responseData'], AxiosError>(
    // TODO: key분리도 해야한다
    ['get-community-articles', studyId, page],
    () => getCommunityArticleListItems({ studyId, page }),
  );
};
