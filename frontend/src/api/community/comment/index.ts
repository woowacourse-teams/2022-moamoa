import { type AxiosError, type AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import { checkType, isNull } from '@utils';

import type { ArticleId, CommunityComment, CommunityCommentId, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

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
