import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';

import type { NoticeArticle } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export type GetNoticeArticlesResponseData = {
  articles: Array<NoticeArticle>;
  currentPage: number;
  lastPage: number;
  totalCount: number;
};

export type GetNoticeArticleResponseData = NoticeArticle;

export type GetNoticeArticlesParams = {
  studyId: number;
  page?: number;
  size?: number;
};

export type GetNoticeArticleParams = {
  studyId: number;
  articleId: number;
};

export type PostNoticeArticleRequestParams = {
  studyId: number;
};
export type PostNoticeArticleRequestBody = {
  title: string;
  content: string;
};
export type PostNoticeArticleRequestVariables = PostNoticeArticleRequestParams & PostNoticeArticleRequestBody;
export type PostNoticeArticleResponseData = {
  studyId: number;
  title: string;
  content: string;
};

export type PutNoticeArticleRequestParams = {
  studyId: number;
  articleId: number;
};
export type PutNoticeArticleRequestBody = {
  title: string;
  content: string;
};
export type PutNoticeArticleRequestVariables = PutNoticeArticleRequestParams & PutNoticeArticleRequestBody;

export type DeleteNoticeArticleRequestParams = {
  studyId: number;
  articleId: number;
};

const getNoticeArticles = async ({ studyId, page = 1, size = 8 }: GetNoticeArticlesParams) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<GetNoticeArticlesResponseData>(
    `/api/studies/${studyId}/notice/articles?page=${page - 1}&size=${size}`,
  );
  const { totalCount, currentPage, lastPage } = response.data;

  response.data = {
    ...response.data,
    totalCount: Number(totalCount),
    currentPage: Number(currentPage) + 1, // page를 하나 늘려준다 서버에서 0으로 오기 때문이다
    lastPage: Number(lastPage),
  };

  return response.data;
};

const getNoticeArticle = async ({ studyId, articleId }: GetNoticeArticleParams) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<GetNoticeArticleResponseData>(
    `/api/studies/${studyId}/notice/articles/${articleId}`,
  );
  return response.data;
};

const postNoticeArticle = async ({ studyId, title, content }: PostNoticeArticleRequestVariables) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, PostNoticeArticleRequestBody>(
    `/api/studies/${studyId}/notice/articles`,
    {
      title,
      content,
    },
  );

  return response.data;
};

const putNoticeArticle = async ({ studyId, title, content, articleId }: PutNoticeArticleRequestVariables) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, PutNoticeArticleRequestBody>(
    `/api/studies/${studyId}/notice/articles/${articleId}`,
    {
      title,
      content,
    },
  );

  return response.data;
};

const deleteNoticeArticle = async ({ studyId, articleId }: DeleteNoticeArticleRequestParams) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>>(
    `/api/studies/${studyId}/notice/articles/${articleId}`,
  );

  return response.data;
};

export const useGetNoticeArticles = (studyId: number, page: number) => {
  return useQuery(['get-notice-articles', studyId, page], () => getNoticeArticles({ studyId, page }));
};

export const useGetNoticeArticle = (studyId: number, articleId: number) => {
  return useQuery(['get-notice-article', studyId, articleId], () => getNoticeArticle({ studyId, articleId }));
};

export const usePostNoticeArticle = () => {
  return useMutation<null, AxiosError, PostNoticeArticleRequestVariables>(postNoticeArticle);
};

export const usePutNoticeArticle = () => {
  return useMutation<null, AxiosError, PutNoticeArticleRequestVariables>(putNoticeArticle);
};

export const useDeleteNoticeArticle = () => {
  return useMutation<null, AxiosError, DeleteNoticeArticleRequestParams>(deleteNoticeArticle);
};
