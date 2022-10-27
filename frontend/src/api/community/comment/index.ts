import { type AxiosError, type AxiosResponse } from 'axios';
import { useInfiniteQuery, useMutation, useQuery } from 'react-query';

import { DEFAULT_COMMUNITY_COMMENT_QUERY_PARAM } from '@constants';

import { checkType, isNull } from '@utils';

import type { ArticleId, CommunityComment, CommunityCommentId, Merge, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkCommunityComments } from '@api/community/comment/typeChecker';

export type ApiCommunityComment = {
  post: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    body: Pick<CommunityComment, 'content'>;
    variables: ApiCommunityComment['post']['params'] & ApiCommunityComment['post']['body'];
  };
  put: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
      communityCommentId: CommunityCommentId;
    };
    body: ApiCommunityComment['post']['body'];
    variables: ApiCommunityComment['put']['params'] & ApiCommunityComment['put']['body'];
  };
  delete: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
      communityCommentId: CommunityCommentId;
    };
    variables: ApiCommunityComment['delete']['params'];
  };
};

export const postCommunityComment = async ({
  studyId,
  articleId,
  content,
}: ApiCommunityComment['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiCommunityComment['post']['body']>(
    `/api/studies/${studyId}/community/${articleId}/comments`,
    {
      content,
    },
  );
  return checkType(response.data, isNull);
};
export const usePostCommunityComment = () =>
  useMutation<null, AxiosError, ApiCommunityComment['post']['variables']>(postCommunityComment);

export const putCommunityComment = async ({
  studyId,
  articleId,
  communityCommentId,
  content,
}: ApiCommunityComment['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiCommunityComment['put']['body']>(
    `/api/studies/${studyId}/community/${articleId}/comments/${communityCommentId}`,
    {
      content,
    },
  );
  return checkType(response.data, isNull);
};
export const usePutCommunityComment = () =>
  useMutation<null, AxiosError, ApiCommunityComment['put']['variables']>(putCommunityComment);

export const deleteCommunityComment = async ({
  studyId,
  articleId,
  communityCommentId,
}: ApiCommunityComment['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, ApiCommunityComment['delete']['variables']>(
    `/api/studies/${studyId}/community/${articleId}/comments/${communityCommentId}`,
  );
  return checkType(response.data, isNull);
};

export const useDeleteCommunityComment = () =>
  useMutation<null, AxiosError, ApiCommunityComment['delete']['variables']>(deleteCommunityComment);

// comments

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
