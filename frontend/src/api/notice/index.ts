import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';

import { checkType, isNull } from '@utils';

import type { ArticleId, NoticeArticle, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkNoticeArticle, checkNoticeArticles } from '@api/notice/typeChecker';

export type ApiNoticeArticles = {
  get: {
    responseData: {
      articles: Array<Omit<NoticeArticle, 'content'>>;
      currentPage: number;
      lastPage: number;
      totalCount: number;
    };
    params: {
      studyId: StudyId;
      page?: Page;
      size?: Size;
    };
    variables: ApiNoticeArticles['get']['params'];
  };
};

export type ApiNoticeArticle = {
  get: {
    responseData: NoticeArticle;
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    variables: ApiNoticeArticle['get']['params'];
  };
  post: {
    params: {
      studyId: StudyId;
    };
    body: Pick<NoticeArticle, 'title' | 'content'>;
    variables: ApiNoticeArticle['post']['params'] & ApiNoticeArticle['post']['body'];
  };
  put: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    body: ApiNoticeArticle['post']['body'];
    variables: ApiNoticeArticle['put']['params'] & ApiNoticeArticle['put']['body'];
  };
  delete: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    variables: ApiNoticeArticle['delete']['params'];
  };
};

const getNoticeArticles = async ({ studyId, page = 1, size = 8 }: ApiNoticeArticles['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiNoticeArticles['get']['responseData']>(
    `/api/studies/${studyId}/notice/articles?page=${page - 1}&size=${size}`,
  );
  const { totalCount, currentPage, lastPage } = response.data;

  response.data = {
    ...response.data,
    totalCount: Number(totalCount),
    currentPage: Number(currentPage) + 1, // page를 하나 늘려준다 서버에서 0으로 오기 때문이다
    lastPage: Number(lastPage),
  };

  return checkNoticeArticles(response.data);
};

const getNoticeArticle = async ({ studyId, articleId }: ApiNoticeArticle['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiNoticeArticle['get']['responseData']>(
    `/api/studies/${studyId}/notice/articles/${articleId}`,
  );
  return checkNoticeArticle(response.data);
};

const postNoticeArticle = async ({ studyId, title, content }: ApiNoticeArticle['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiNoticeArticle['post']['body']>(
    `/api/studies/${studyId}/notice/articles`,
    {
      title,
      content,
    },
  );

  return checkType(response.data, isNull);
};

const putNoticeArticle = async ({ studyId, title, content, articleId }: ApiNoticeArticle['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiNoticeArticle['put']['body']>(
    `/api/studies/${studyId}/notice/articles/${articleId}`,
    {
      title,
      content,
    },
  );

  return checkType(response.data, isNull);
};

const deleteNoticeArticle = async ({ studyId, articleId }: ApiNoticeArticle['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>>(
    `/api/studies/${studyId}/notice/articles/${articleId}`,
  );

  return checkType(response.data, isNull);
};

export const useGetNoticeArticles = ({ studyId, page }: ApiNoticeArticles['get']['variables']) => {
  return useQuery(['get-notice-articles', studyId, page], () => getNoticeArticles({ studyId, page }));
};

export const useGetNoticeArticle = ({ studyId, articleId }: ApiNoticeArticle['get']['variables']) => {
  return useQuery(['get-notice-article', studyId, articleId], () => getNoticeArticle({ studyId, articleId }));
};

export const usePostNoticeArticle = () => {
  return useMutation<null, AxiosError, ApiNoticeArticle['post']['variables']>(postNoticeArticle);
};

export const usePutNoticeArticle = () => {
  return useMutation<null, AxiosError, ApiNoticeArticle['put']['variables']>(putNoticeArticle);
};

export const useDeleteNoticeArticle = () => {
  return useMutation<null, AxiosError, ApiNoticeArticle['delete']['variables']>(deleteNoticeArticle);
};
