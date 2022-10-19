import { type AxiosError, type AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';

import { checkType, isNull } from '@utils';

import type { ArticleId, DraftArtcle, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkCommunityDraftArticle, checkDraftArticleId } from '@api/community/draft-article/typeChecker';

export type ApiCommunityDraftArticle = {
  get: {
    responseData: DraftArtcle;
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    variables: ApiCommunityDraftArticle['get']['params'];
  };
  post: {
    responseData: { draftArticleId: ArticleId };
    params: {
      studyId: StudyId;
    };
    body: Pick<DraftArtcle, 'title' | 'content'>;
    variables: ApiCommunityDraftArticle['post']['params'] & ApiCommunityDraftArticle['post']['body'];
  };
  put: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    body: ApiCommunityDraftArticle['post']['body'];
    variables: ApiCommunityDraftArticle['put']['params'] & ApiCommunityDraftArticle['put']['body'];
  };
  delete: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    variables: ApiCommunityDraftArticle['delete']['params'];
  };
};

// get
const getCommunityDraftArticle = async ({ studyId, articleId }: ApiCommunityDraftArticle['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiCommunityDraftArticle['get']['responseData']>(
    `/api/studies/${studyId}/community/draft-articles/${articleId}`,
  );

  return checkCommunityDraftArticle(response.data);
};

export const useGetCommunityDraftArticle = ({ studyId, articleId }: ApiCommunityDraftArticle['get']['variables']) => {
  return useQuery<ApiCommunityDraftArticle['get']['responseData'], AxiosError>(
    ['get-community-draft-article', studyId, articleId],
    () => getCommunityDraftArticle({ studyId, articleId }),
  );
};

// post
const postCommunityDraftArticle = async ({
  studyId,
  title,
  content,
}: ApiCommunityDraftArticle['post']['variables']) => {
  const response = await axiosInstance.post<
    ApiCommunityDraftArticle['post']['responseData'],
    AxiosResponse<ApiCommunityDraftArticle['post']['responseData']>,
    ApiCommunityDraftArticle['post']['body']
  >(`/api/studies/${studyId}/community/draft-articles`, {
    title,
    content,
  });

  return checkDraftArticleId(response.data);
};

export const usePostDraftArticle = () => {
  return useMutation<
    ApiCommunityDraftArticle['post']['responseData'],
    AxiosError,
    ApiCommunityDraftArticle['post']['variables']
  >(postCommunityDraftArticle);
};

// put
const putCommunityDraftArticle = async ({
  studyId,
  articleId,
  title,
  content,
}: ApiCommunityDraftArticle['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiCommunityDraftArticle['put']['body']>(
    `/api/studies/${studyId}/community/draft-articles/${articleId}`,
    {
      title,
      content,
    },
  );

  return checkType(response.data, isNull);
};

export const usePutCommunityArticle = () => {
  return useMutation<null, AxiosError, ApiCommunityDraftArticle['put']['variables']>(putCommunityDraftArticle);
};

// delete
const deleteCommunityDraftArticle = async ({ studyId, articleId }: ApiCommunityDraftArticle['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>>(
    `/api/studies/${studyId}/community/draft-articles/${articleId}`,
  );

  return checkType(response.data, isNull);
};

export const useDeleteCommunityArticle = () => {
  return useMutation<null, AxiosError, ApiCommunityDraftArticle['delete']['variables']>(deleteCommunityDraftArticle);
};
