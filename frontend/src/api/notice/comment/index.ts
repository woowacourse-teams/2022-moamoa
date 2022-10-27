import { type AxiosError, type AxiosResponse } from 'axios';
import { useInfiniteQuery, useMutation, useQuery } from 'react-query';

import { DEFAULT_NOTICE_COMMENT_QUERY_PARAM } from '@constants';

import { checkType, isNull } from '@utils';

import type { ArticleId, Merge, NoticeComment, NoticeCommentId, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkInfiniteNoticeComments, checkNoticeComments } from '@api/notice/comment/typeChecker';

export type ApiNoticeComment = {
  post: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    body: Pick<NoticeComment, 'content'>;
    variables: ApiNoticeComment['post']['params'] & ApiNoticeComment['post']['body'];
  };
  put: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
      noticeCommentId: NoticeCommentId;
    };
    body: ApiNoticeComment['post']['body'];
    variables: ApiNoticeComment['put']['params'] & ApiNoticeComment['put']['body'];
  };
  delete: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
      noticeCommentId: NoticeCommentId;
    };
    variables: ApiNoticeComment['delete']['params'];
  };
};

export const postNoticeComment = async ({ studyId, articleId, content }: ApiNoticeComment['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiNoticeComment['post']['body']>(
    `/api/studies/${studyId}/community/${articleId}/comments`,
    {
      content,
    },
  );
  return checkType(response.data, isNull);
};
export const usePostNoticeComment = () =>
  useMutation<null, AxiosError, ApiNoticeComment['post']['variables']>(postNoticeComment);

export const putNoticeComment = async ({
  studyId,
  articleId,
  noticeCommentId,
  content,
}: ApiNoticeComment['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiNoticeComment['put']['body']>(
    `/api/studies/${studyId}/community/${articleId}/comments/${noticeCommentId}`,
    {
      content,
    },
  );
  return checkType(response.data, isNull);
};
export const usePutNoticeComment = () =>
  useMutation<null, AxiosError, ApiNoticeComment['put']['variables']>(putNoticeComment);

export const deleteNoticeComment = async ({
  studyId,
  articleId,
  noticeCommentId,
}: ApiNoticeComment['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, ApiNoticeComment['delete']['variables']>(
    `/api/studies/${studyId}/community/${articleId}/comments/${noticeCommentId}`,
  );
  return checkType(response.data, isNull);
};

export const useDeleteNoticeComment = () =>
  useMutation<null, AxiosError, ApiNoticeComment['delete']['variables']>(deleteNoticeComment);

// comments

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
