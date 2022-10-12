import { AxiosError, type AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';

import { checkType, isNull } from '@utils';

import type { ArticleId, CommunityArticle, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkCommunityArticle, checkCommunityArticles } from '@api/community/typeChecker';

export type ApiCommunityArticles = {
  get: {
    responseData: {
      articles: Array<Omit<CommunityArticle, 'content'>>;
      currentPage: number;
      lastPage: number;
      totalCount: number;
    };
    params: {
      studyId: StudyId;
      page?: Page;
      size?: Size;
    };
    variables: ApiCommunityArticles['get']['params'];
  };
};

export type ApiCommunityArticle = {
  get: {
    responseData: CommunityArticle;
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    variables: ApiCommunityArticle['get']['params'];
  };
  post: {
    params: {
      studyId: StudyId;
    };
    body: Pick<CommunityArticle, 'title' | 'content'>;
    variables: ApiCommunityArticle['post']['params'] & ApiCommunityArticle['post']['body'];
  };
  put: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    body: ApiCommunityArticle['post']['body'];
    variables: ApiCommunityArticle['put']['params'] & ApiCommunityArticle['put']['body'];
  };
  delete: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    variables: ApiCommunityArticle['delete']['params'];
  };
};

const getCommunityArticles = async ({ studyId, page = 1, size = 8 }: ApiCommunityArticles['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiCommunityArticles['get']['responseData']>(
    `/api/studies/${studyId}/community/articles?page=${page - 1}&size=${size}`,
  );

  return checkCommunityArticles(response.data);
};

// articles
export const useGetCommunityArticles = ({ studyId, page }: ApiCommunityArticles['get']['variables']) => {
  return useQuery<ApiCommunityArticles['get']['responseData'], AxiosError>(
    ['get-community-articles', studyId, page],
    () => getCommunityArticles({ studyId, page }),
  );
};

// get
const getCommunityArticle = async ({ studyId, articleId }: ApiCommunityArticle['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiCommunityArticle['get']['responseData']>(
    `/api/studies/${studyId}/community/articles/${articleId}`,
  );

  return checkCommunityArticle(response.data);
};

export const useGetCommunityArticle = ({ studyId, articleId }: ApiCommunityArticle['get']['variables']) => {
  return useQuery<ApiCommunityArticle['get']['responseData'], AxiosError>(
    ['get-community-article', studyId, articleId],
    () => getCommunityArticle({ studyId, articleId }),
  );
};

// post
const postCommunityArticle = async ({ studyId, title, content }: ApiCommunityArticle['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiCommunityArticle['post']['body']>(
    `/api/studies/${studyId}/community/articles`,
    {
      title,
      content,
    },
  );

  return checkType(response.data, isNull);
};

export const usePostCommunityArticle = () => {
  return useMutation<null, AxiosError, ApiCommunityArticle['post']['variables']>(postCommunityArticle);
};

// put
const putCommunityArticle = async ({ studyId, title, content, articleId }: ApiCommunityArticle['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiCommunityArticle['put']['body']>(
    `/api/studies/${studyId}/community/articles/${articleId}`,
    {
      title,
      content,
    },
  );

  return checkType(response.data, isNull);
};

export const usePutCommunityArticle = () => {
  return useMutation<null, AxiosError, ApiCommunityArticle['put']['variables']>(putCommunityArticle);
};

// delete
const deleteCommunityArticle = async ({ studyId, articleId }: ApiCommunityArticle['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>>(
    `/api/studies/${studyId}/community/articles/${articleId}`,
  );

  return checkType(response.data, isNull);
};

export const useDeleteCommunityArticle = () => {
  return useMutation<null, AxiosError, ApiCommunityArticle['delete']['variables']>(deleteCommunityArticle);
};
