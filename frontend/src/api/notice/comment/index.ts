import { type AxiosError, type AxiosResponse } from 'axios';
import { useInfiniteQuery, useMutation, useQuery } from 'react-query';

import { DEFAULT_NOTICE_COMMENT_QUERY_PARAM } from '@constants';

import { checkType, isNull } from '@utils';

import type { ArticleId, Merge, NoticeComment, NoticeCommentId, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkNoticeComments } from '@api/notice/comment/typeChecker';

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
export const useGetNoticeComments = ({ studyId, articleId, size }: ApiNoticeComments['get']['variables']) => {
  return useQuery([QK_NOTICE_COMMENTS, size, studyId], () => getNoticeComments({ studyId, articleId, size }));
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
