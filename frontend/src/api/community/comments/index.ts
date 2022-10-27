import { type AxiosError } from 'axios';
import { useInfiniteQuery, useQuery } from 'react-query';

import { DEFAULT_COMMUNITY_COMMENT_QUERY_PARAM } from '@constants';

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

export const getCommunityComments = async ({
  studyId,
  articleId,
  size,
  page,
}: ApiCommunityComments['get']['variables']) => {
  let url = `/api/studies/${studyId}/community/${articleId}/comments`;
  if (size) {
    // @TODO: withSizeOrPage 함수 만들어서 재활용 하자
    url = `${url}?size=${size}`;
    if (page) url = `${url}?page=${page}`;
  }
  const response = await axiosInstance.get<ApiCommunityComments['get']['responseData']>(url);
  return checkCommunityComments(response.data);
};
export const useGetCommunityComments = ({ studyId, articleId, size }: ApiCommunityComments['get']['variables']) => {
  return useQuery([QK_COMMUNITY_COMMENTS, size, studyId], () => getCommunityComments({ studyId, articleId, size }));
};

export const getCommunityCommentsWithPage =
  ({ studyId, articleId, size = SIZE }: ApiCommunityComments['get']['variables']) =>
  async ({
    pageParam = defaultParam,
  }): Promise<Merge<ApiCommunityComments['get']['responseData'], { page: number }>> => {
    const data = await getCommunityComments({ studyId, articleId, size, ...pageParam });
    return { ...data, page: pageParam.page + 1 };
  };

export const useGetInfiniteCommunityComments = ({
  studyId,
  articleId,
  size,
}: ApiCommunityComments['get']['variables']) => {
  return useInfiniteQuery<Merge<ApiCommunityComments['get']['responseData'], { page: Page }>, AxiosError>(
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
