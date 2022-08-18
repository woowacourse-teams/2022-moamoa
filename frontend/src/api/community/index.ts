import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';

import type { CommunityArticle } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export type GetCommunityArticlesResponseData = {
  articles: Array<CommunityArticle>;
  currentPage: number;
  lastPage: number;
  totalCount: number;
};

export type GetCommunityArticleResponseData = CommunityArticle;

export type GetCommunityArticlesParams = {
  studyId: number;
  page?: number;
  size?: number;
};

export type GetCommunityArticleParams = {
  studyId: number;
  articleId: number;
};

export type PostCommunityArticleRequestParams = {
  studyId: number;
};
export type PostCommunityArticleRequestBody = {
  title: string;
  content: string;
};
export type PostCommunityArticleRequestVariables = PostCommunityArticleRequestParams & PostCommunityArticleRequestBody;
export type PostCommunityArticleResponseData = {
  studyId: number;
  title: string;
  content: string;
};

export type PutCommunityArticleRequestParams = {
  studyId: number;
  articleId: number;
};
export type PutCommunityArticleRequestBody = {
  title: string;
  content: string;
};
export type PutCommunityArticleRequestVariables = PutCommunityArticleRequestParams & PutCommunityArticleRequestBody;

export type DeleteCommunityArticleRequestParams = {
  studyId: number;
  articleId: number;
};

const getCommunityArticles = async ({ studyId, page = 1, size = 8 }: GetCommunityArticlesParams) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<GetCommunityArticlesResponseData>(
    `/api/studies/${studyId}/community/articles?page=${page - 1}&size=${size}`,
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

const getCommunityArticle = async ({ studyId, articleId }: GetCommunityArticleParams) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<GetCommunityArticleResponseData>(
    `/api/studies/${studyId}/community/articles/${articleId}`,
  );
  return response.data;
};

const postCommunityArticle = async ({ studyId, title, content }: PostCommunityArticleRequestVariables) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, PostCommunityArticleRequestBody>(
    `/api/studies/${studyId}/community/articles`,
    {
      title,
      content,
    },
  );

  return response.data;
};

const putCommunityArticle = async ({ studyId, title, content, articleId }: PutCommunityArticleRequestVariables) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, PutCommunityArticleRequestBody>(
    `/api/studies/${studyId}/community/articles/${articleId}`,
    {
      title,
      content,
    },
  );

  return response.data;
};

const deleteCommunityArticle = async ({ studyId, articleId }: DeleteCommunityArticleRequestParams) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>>(
    `/api/studies/${studyId}/community/articles/${articleId}`,
  );

  return response.data;
};

export const useGetCommunityArticles = (studyId: number, page: number) => {
  return useQuery(['get-community-articles', studyId, page], () => getCommunityArticles({ studyId, page }));
};

export const useGetCommunityArticle = (studyId: number, articleId: number) => {
  return useQuery(['get-community-article', studyId, articleId], () => getCommunityArticle({ studyId, articleId }));
};

export const usePostCommunityArticle = () => {
  return useMutation<null, AxiosError, PostCommunityArticleRequestVariables>(postCommunityArticle);
};

export const usePutCommunityArticle = () => {
  return useMutation<null, AxiosError, PutCommunityArticleRequestVariables>(putCommunityArticle);
};

export const useDeleteCommunityArticle = () => {
  return useMutation<null, AxiosError, DeleteCommunityArticleRequestParams>(deleteCommunityArticle);
};
