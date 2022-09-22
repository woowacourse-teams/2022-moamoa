import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';

import type { CommunityArticle } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export type ApiCommunityArticles = {
  get: {
    responseData: {
      articles: Array<CommunityArticle>;
      currentPage: number;
      lastPage: number;
      totalCount: number;
    };
    params: {
      studyId: number;
      page?: number;
      size?: number;
    };
    variables: ApiCommunityArticles['get']['params'];
  };
  post: {
    responseData: CommunityArticle;
    params: {
      studyId: number;
      articleId: number;
    };
    variables: ApiCommunityArticles['post']['params'];
  };
};

export type ApiCommunityArticle = {
  get: {
    responseData: CommunityArticle;
    params: {
      studyId: number;
      articleId: number;
    };
    variables: ApiCommunityArticle['get']['params'];
  };
  post: {
    responseData: {
      studyId: number;
      title: string;
      content: string;
    };
    params: {
      studyId: number;
    };
    body: {
      title: string;
      content: string;
    };
    variables: ApiCommunityArticle['post']['params'] & ApiCommunityArticle['post']['body'];
  };
  put: {
    params: {
      studyId: number;
      articleId: number;
    };
    body: {
      title: string;
      content: string;
    };
    variables: ApiCommunityArticle['put']['params'] & ApiCommunityArticle['put']['body'];
  };
  delete: {
    params: {
      studyId: number;
      articleId: number;
    };
    variables: ApiCommunityArticle['delete']['params'];
  };
};

const getCommunityArticles = async ({ studyId, page = 1, size = 8 }: ApiCommunityArticles['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiCommunityArticles['get']['responseData']>(
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

const getCommunityArticle = async ({ studyId, articleId }: ApiCommunityArticle['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiCommunityArticle['get']['responseData']>(
    `/api/studies/${studyId}/community/articles/${articleId}`,
  );
  return response.data;
};

const postCommunityArticle = async ({ studyId, title, content }: ApiCommunityArticle['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiCommunityArticle['post']['body']>(
    `/api/studies/${studyId}/community/articles`,
    {
      title,
      content,
    },
  );

  return response.data;
};

const putCommunityArticle = async ({ studyId, title, content, articleId }: ApiCommunityArticle['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiCommunityArticle['put']['body']>(
    `/api/studies/${studyId}/community/articles/${articleId}`,
    {
      title,
      content,
    },
  );

  return response.data;
};

const deleteCommunityArticle = async ({ studyId, articleId }: ApiCommunityArticle['delete']['variables']) => {
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
  return useMutation<null, AxiosError, ApiCommunityArticle['post']['variables']>(postCommunityArticle);
};

export const usePutCommunityArticle = () => {
  return useMutation<null, AxiosError, ApiCommunityArticle['put']['variables']>(putCommunityArticle);
};

export const useDeleteCommunityArticle = () => {
  return useMutation<null, AxiosError, ApiCommunityArticle['delete']['variables']>(deleteCommunityArticle);
};
