import { type AxiosError } from 'axios';
import { useInfiniteQuery, useQuery } from 'react-query';

import { DEFAULT_NOTICE_COMMENT_QUERY_PARAM } from '@constants';

import type { ArticleId, Merge, NoticeComment, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkInfiniteNoticeComments, checkNoticeComments } from '@api/notice-comments/typeChecker';

export const QK_NOTICE_COMMENTS = 'notice-comments';
export const QK_NOTICE_COMMENTS_INFINITE_SCROLL = 'infinite-scroll-notice-comments';

export type ApiNoticeComments = {
  get: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
      size?: Size;
    };
    responseData: {
      comments: Array<NoticeComment>;
      totalCount: number;
    };
    variables: ApiNoticeComments['get']['params'];
  };
};

export type ApiInfiniteNoticeComments = {
  get: {
    params: Merge<ApiNoticeComments['get']['params'], { size?: Size }>;
    responseData: Merge<ApiNoticeComments['get']['responseData'], { hasNext: boolean; totalCount: number }>;
    variables: ApiInfiniteNoticeComments['get']['params'];
  };
};

type PageParam = { page: number };
type NextPageParam = PageParam | undefined;

const { PAGE, SIZE } = DEFAULT_NOTICE_COMMENT_QUERY_PARAM;
const defaultParam: PageParam = {
  page: PAGE,
};

export const getNoticeComments = async ({ studyId, articleId, size }: ApiNoticeComments['get']['variables']) => {
  const url = size
    ? `/api/studies/${studyId}/notice/${articleId}/comments?size=${size}`
    : `/api/studies/${studyId}/notice/${articleId}/comments`;
  const response = await axiosInstance.get<ApiNoticeComments['get']['responseData']>(url);
  return checkNoticeComments(response.data);
};
export const useGetNoticeComments = ({ studyId, articleId, size }: ApiNoticeComments['get']['variables']) => {
  return useQuery([QK_NOTICE_COMMENTS, size, studyId], () => getNoticeComments({ studyId, articleId, size }));
};

export const getNoticeCommentsWithPage =
  ({ studyId, articleId, size = SIZE }: ApiInfiniteNoticeComments['get']['variables']) =>
  async ({
    pageParam = defaultParam,
  }): Promise<Merge<ApiInfiniteNoticeComments['get']['responseData'], { page: Page }>> => {
    const url = `/api/studies/${studyId}/notice/${articleId}/comments?size=${size}&page=${pageParam.page}`;
    const response = await axiosInstance.get<ApiInfiniteNoticeComments['get']['responseData']>(url);
    const { data } = response;
    return { ...checkInfiniteNoticeComments(data), page: pageParam.page };
  };

export const useGetInfiniteNoticeComments = ({
  studyId,
  articleId,
  size,
}: ApiInfiniteNoticeComments['get']['variables']) => {
  return useInfiniteQuery<Merge<ApiInfiniteNoticeComments['get']['responseData'], { page: Page }>, AxiosError>(
    [QK_NOTICE_COMMENTS_INFINITE_SCROLL, studyId, articleId],
    getNoticeCommentsWithPage({ studyId, articleId, size }),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return;
        return { page: lastPage.page + 1 };
      },
    },
  );
};
