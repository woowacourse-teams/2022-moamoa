import { type AxiosError } from 'axios';
import { useInfiniteQuery } from 'react-query';

import { DEFAULT_COMMUNITY_COMMENT_QUERY_PARAM } from '@constants';

import { buildURLQuery } from '@utils';

import type { ArticleId, CommunityComment, Merge, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkCommunityComments } from '@api/community/comments/typeChecker';

export const QK_COMMUNITY_COMMENTS = 'community-comments';
export const QK_COMMUNITY_COMMENTS_INFINITE_SCROLL = 'infinite-scroll-community-comments';

export type ApiCommunityComments = {
  get: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
      size?: Size;
      page?: Page;
    };
    responseData: {
      comments: Array<CommunityComment>;
      totalCount: number;
      hasNext: boolean;
    };
    variables: ApiCommunityComments['get']['params'];
  };
};

type PageParam = { page: number };
type NextPageParam = PageParam | undefined;

const { PAGE, SIZE } = DEFAULT_COMMUNITY_COMMENT_QUERY_PARAM;
const defaultParam: PageParam = {
  page: PAGE,
};

const getCommunityComments =
  ({ studyId, articleId, size = SIZE }: ApiCommunityComments['get']['variables']) =>
  async ({
    pageParam = defaultParam,
  }): Promise<Merge<ApiCommunityComments['get']['responseData'], { page: number }>> => {
    const url = buildURLQuery(`/api/studies/${studyId}/community/${articleId}/comments`, {
      page: pageParam.page,
      size,
    });
    const response = await axiosInstance.get<ApiCommunityComments['get']['responseData']>(url);
    const data = checkCommunityComments(response.data);
    return { ...data, page: pageParam.page + 1 };
  };

export const useGetInfiniteCommunityComments = ({
  studyId,
  articleId,
  size,
}: ApiCommunityComments['get']['variables']) => {
  return useInfiniteQuery<Merge<ApiCommunityComments['get']['responseData'], { page: Page }>, AxiosError>(
    [QK_COMMUNITY_COMMENTS_INFINITE_SCROLL, studyId, articleId],
    getCommunityComments({ studyId, articleId, size }),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return;
        return { page: lastPage.page + 1 };
      },
    },
  );
};
