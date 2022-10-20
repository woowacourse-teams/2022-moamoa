import { type AxiosError } from 'axios';
import { useInfiniteQuery, useQuery } from 'react-query';

import { DEFAULT_COMMUNITY_COMMENT_QUERY_PARAM } from '@constants';

import type { ArticleId, CommunityComment, Merge, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkCommunityComments, checkInfiniteCommunityComments } from '@api/community-comments/typeChecker';

export const QK_COMMUNITY_COMMENTS = 'community-comments';
export const QK_COMMUNITY_COMMENTS_INFINITE_SCROLL = 'infinite-scroll-community-comments';

export type ApiCommunityComments = {
  get: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
      size?: Size;
    };
    responseData: {
      comments: Array<CommunityComment>;
      totalCount: number;
    };
    variables: ApiCommunityComments['get']['params'];
  };
};

export type ApiInfiniteCommunityComments = {
  get: {
    params: Merge<ApiCommunityComments['get']['params'], { size?: Size }>;
    responseData: Merge<ApiCommunityComments['get']['responseData'], { hasNext: boolean; totalCount: number }>;
    variables: ApiInfiniteCommunityComments['get']['params'];
  };
};

type PageParam = { page: number };
type NextPageParam = PageParam | undefined;

const { PAGE, SIZE } = DEFAULT_COMMUNITY_COMMENT_QUERY_PARAM;
const defaultParam: PageParam = {
  page: PAGE,
};

export const getCommunityComments = async ({ studyId, articleId, size }: ApiCommunityComments['get']['variables']) => {
  const url = size
    ? `/api/studies/${studyId}/community/${articleId}/comments?size=${size}`
    : `/api/studies/${studyId}/community/${articleId}/comments`;
  const response = await axiosInstance.get<ApiCommunityComments['get']['responseData']>(url);
  return checkCommunityComments(response.data);
};
export const useGetCommunityComments = ({ studyId, articleId, size }: ApiCommunityComments['get']['variables']) => {
  return useQuery([QK_COMMUNITY_COMMENTS, size, studyId], () => getCommunityComments({ studyId, articleId, size }));
};

export const getCommunityCommentsWithPage =
  ({ studyId, articleId, size = SIZE }: ApiInfiniteCommunityComments['get']['variables']) =>
  async ({
    pageParam = defaultParam,
  }): Promise<Merge<ApiInfiniteCommunityComments['get']['responseData'], { page: Page }>> => {
    const url = `/api/studies/${studyId}/community/${articleId}/comments?size=${size}&page=${pageParam.page}`;
    const response = await axiosInstance.get<ApiInfiniteCommunityComments['get']['responseData']>(url);
    return { ...checkInfiniteCommunityComments(response.data), page: pageParam.page };
  };

export const useGetInfiniteCommunityComments = ({
  studyId,
  articleId,
  size,
}: ApiInfiniteCommunityComments['get']['variables']) => {
  return useInfiniteQuery<Merge<ApiInfiniteCommunityComments['get']['responseData'], { page: Page }>, AxiosError>(
    [QK_COMMUNITY_COMMENTS_INFINITE_SCROLL, studyId, articleId],
    getCommunityCommentsWithPage({ studyId, articleId, size }),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return;
        return { page: lastPage.page + 1 };
      },
    },
  );
};
