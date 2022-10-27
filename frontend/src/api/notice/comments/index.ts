import { AxiosError } from 'axios';
import { useInfiniteQuery, useQuery } from 'react-query';

import { DEFAULT_NOTICE_COMMENT_QUERY_PARAM } from '@constants';

import type { ArticleId, Merge, NoticeComment, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkNoticeComments } from '@api/notice/comments/typeChecker';

export const QK_NOTICE_COMMENTS = 'notice-comments';
export const QK_NOTICE_COMMENTS_INFINITE_SCROLL = 'infinite-scroll-notice-comments';

export type ApiNoticeComments = {
  get: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
      size?: Size;
      page?: Page;
    };
    responseData: {
      comments: Array<NoticeComment>;
      totalCount: number;
      hasNext: boolean;
    };
    variables: ApiNoticeComments['get']['params'];
  };
};

type PageParam = { page: number };
type NextPageParam = PageParam | undefined;

const { PAGE, SIZE } = DEFAULT_NOTICE_COMMENT_QUERY_PARAM;
const defaultParam: PageParam = {
  page: PAGE,
};

export const getNoticeComments = async ({ studyId, articleId, size, page }: ApiNoticeComments['get']['variables']) => {
  let url = `/api/studies/${studyId}/notice/${articleId}/comments`;
  if (size) {
    url = `${url}?size=${size}`;
    if (page) url = `${url}?page=${page}`;
  }
  const response = await axiosInstance.get<ApiNoticeComments['get']['responseData']>(url);
  return checkNoticeComments(response.data);
};

export const getNoticeCommentsWithPage =
  ({ studyId, articleId, size = SIZE }: ApiNoticeComments['get']['variables']) =>
  async ({ pageParam = defaultParam }): Promise<Merge<ApiNoticeComments['get']['responseData'], { page: Page }>> => {
    const data = await getNoticeComments({ studyId, articleId, size, ...pageParam });
    return { ...data, page: pageParam.page + 1 };
  };
export const useGetInfiniteNoticeComments = ({ studyId, articleId, size }: ApiNoticeComments['get']['variables']) => {
  return useInfiniteQuery<Merge<ApiNoticeComments['get']['responseData'], { page: Page }>, AxiosError>(
    [QK_NOTICE_COMMENTS_INFINITE_SCROLL, studyId, articleId],
    getNoticeCommentsWithPage({ studyId, articleId, size }),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return;
        return { page: lastPage.page };
      },
    },
  );
};
