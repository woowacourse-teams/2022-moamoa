import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { DraftArtcle, StudyDetail, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkCommunityDraftArticles } from '@api/community/draft-articles/typeChecker';

export type ApiCommunityDraftArticles = {
  get: {
    responseData: {
      articles: Array<
        Omit<DraftArtcle, 'content'> & {
          study: {
            id: StudyId;
            title: StudyDetail['title'];
          };
        }
      >;
      currentPage: number;
      lastPage: number;
      totalCount: number;
    };
  };
};

const getCommunityDraftArticles = async () => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiCommunityDraftArticles['get']['responseData']>(
    `/api/draft/community/articles`,
  );

  return checkCommunityDraftArticles(response.data);
};

export const useGetCommunityArticles = () => {
  return useQuery<ApiCommunityDraftArticles['get']['responseData'], AxiosError>(
    ['get-community-draft-articles'],
    getCommunityDraftArticles,
  );
};
