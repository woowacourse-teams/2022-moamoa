import { type AxiosError, type AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';

import { checkType, isNull } from '@utils';

import type { ArticleId, CommunityArticle, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkCommunityArticleDetail } from '@api/community/article-detail/typeChecker';

export type ApiCommunityArticleDetail = {
  get: {
    responseData: CommunityArticle;
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    variables: ApiCommunityArticleDetail['get']['params'];
  };
  post: {
    params: {
      studyId: StudyId;
    };
    body: Pick<CommunityArticle, 'title' | 'content'>;
    variables: ApiCommunityArticleDetail['post']['params'] & ApiCommunityArticleDetail['post']['body'];
  };
  put: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    body: ApiCommunityArticleDetail['post']['body'];
    variables: ApiCommunityArticleDetail['put']['params'] & ApiCommunityArticleDetail['put']['body'];
  };
  delete: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    variables: ApiCommunityArticleDetail['delete']['params'];
  };
};

// get
const getCommunityArticleDetail = async ({ studyId, articleId }: ApiCommunityArticleDetail['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiCommunityArticleDetail['get']['responseData']>(
    `/api/studies/${studyId}/community/articles/${articleId}`,
  );

  return checkCommunityArticleDetail(response.data);
};

export const useGetCommunityArticleDetail = ({ studyId, articleId }: ApiCommunityArticleDetail['get']['variables']) => {
  return useQuery<ApiCommunityArticleDetail['get']['responseData'], AxiosError>(
    ['get-community-article', studyId, articleId],
    () => getCommunityArticleDetail({ studyId, articleId }),
  );
};

// post
const postCommunityArticleDetail = async ({
  studyId,
  title,
  content,
}: ApiCommunityArticleDetail['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiCommunityArticleDetail['post']['body']>(
    `/api/studies/${studyId}/community/articles`,
    {
      title,
      content,
    },
  );

  return checkType(response.data, isNull);
};

export const usePostCommunityArticleDetail = () => {
  return useMutation<null, AxiosError, ApiCommunityArticleDetail['post']['variables']>(postCommunityArticleDetail);
};

// put
const putCommunityArticleDetail = async ({
  studyId,
  title,
  content,
  articleId,
}: ApiCommunityArticleDetail['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiCommunityArticleDetail['put']['body']>(
    `/api/studies/${studyId}/community/articles/${articleId}`,
    {
      title,
      content,
    },
  );

  return checkType(response.data, isNull);
};

export const usePutCommunityArticleDetail = () => {
  return useMutation<null, AxiosError, ApiCommunityArticleDetail['put']['variables']>(putCommunityArticleDetail);
};

// delete
const deleteCommunityArticleDetail = async ({
  studyId,
  articleId,
}: ApiCommunityArticleDetail['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>>(
    `/api/studies/${studyId}/community/articles/${articleId}`,
  );

  return checkType(response.data, isNull);
};

export const useDeleteCommunityArticleDetail = () => {
  return useMutation<null, AxiosError, ApiCommunityArticleDetail['delete']['variables']>(deleteCommunityArticleDetail);
};
