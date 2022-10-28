import { AxiosError } from 'axios';
import { useInfiniteQuery } from 'react-query';

import { DEFAULT_COMMUNITY_ARTICLE_DRAFT_QUERY_PARAM } from '@constants';

import buildURLQuery from '@utils/buildURLQuery';

import type { DraftArtcle, Page, Size, StudyDetail, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkCommunityDraftArticles } from '@api/community/draft-articles/typeChecker';

export type ApiCommunityDraftArticles = {
  get: {
    responseData: {
      draftArticles: Array<
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
    params: {
      page?: Page;
      size?: Size;
    };
    variables: ApiCommunityDraftArticles['get']['params'];
  };
};

const getCommunityDraftArticles = async ({ page, size }: ApiCommunityDraftArticles['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiCommunityDraftArticles['get']['responseData']>(
    buildURLQuery(`/api/draft/community/articles`, { page, size }),
  );

  return checkCommunityDraftArticles(response.data);
};

type PageParam = { page: Page };
type NextPageParam = PageParam | undefined;

export type ApiInfiniteCommunityDraftArticles = {
  get: {
    params: { studyId: StudyId };
    responseData: ApiCommunityDraftArticles['get']['responseData'] & PageParam;
    variables: ApiInfiniteCommunityDraftArticles['get']['params'];
  };
};

const defaultParam: PageParam = {
  page: DEFAULT_COMMUNITY_ARTICLE_DRAFT_QUERY_PARAM.PAGE,
};

const getCommunityDraftArticlesWithPage = async ({
  pageParam = defaultParam,
}): Promise<ApiInfiniteCommunityDraftArticles['get']['responseData']> => {
  const data = await getCommunityDraftArticles({
    ...pageParam,
    size: DEFAULT_COMMUNITY_ARTICLE_DRAFT_QUERY_PARAM.SIZE,
  });
  return { ...data, page: pageParam.page + 1 };
};

export const useGetInfiniteCommunityDraftArticles = () => {
  return useInfiniteQuery<ApiInfiniteCommunityDraftArticles['get']['responseData'], AxiosError>(
    ['get-community-draft-articles'],
    getCommunityDraftArticlesWithPage,
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (lastPage.currentPage >= lastPage.totalCount) return;
        return { page: lastPage.page };
      },
    },
  );
};
