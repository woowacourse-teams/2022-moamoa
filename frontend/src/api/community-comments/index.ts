import { type AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { ArticleId, CommunityComment, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkCommunityComments } from '@api/community-comments/typeChecker';

export const QK_COMMUNITY_COMMENTS = 'community-comments';

export type ApiCommunityComments = {
  get: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
      size?: Size;
    };
    responseData: {
      comments: Array<CommunityComment>;
      totalCount: number;
    };
    variables: ApiCommunityComments['get']['params'];
  };
};

export const getCommunityComments = async ({ studyId, articleId, size }: ApiCommunityComments['get']['variables']) => {
  const url = size
    ? `/api/studies/${studyId}/community/${articleId}/comments?size=${size}`
    : `/api/studies/${studyId}/community/${articleId}/comments`;
  const response = await axiosInstance.get<ApiCommunityComments['get']['responseData']>(url);
  return checkCommunityComments(response);
};

export const useGetCommunityComments = ({ studyId, articleId, size }: ApiCommunityComments['get']['variables']) => {
  const queryKey = size
    ? [QK_COMMUNITY_COMMENTS, studyId, articleId]
    : [QK_COMMUNITY_COMMENTS, studyId, articleId, 'all'];
  return useQuery<ApiCommunityComments['get']['responseData'], AxiosError>(queryKey, () =>
    getCommunityComments({ studyId, articleId, size }),
  );
};
