import { type AxiosError, type AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import { checkType, isNull } from '@utils';

import type { ArticleId, NoticeComment, NoticeCommentId, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

export type ApiNoticeComment = {
  post: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    body: Pick<NoticeComment, 'content'>;
    variables: ApiNoticeComment['post']['params'] & ApiNoticeComment['post']['body'];
  };
  put: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
      noticeCommentId: NoticeCommentId;
    };
    body: ApiNoticeComment['post']['body'];
    variables: ApiNoticeComment['put']['params'] & ApiNoticeComment['put']['body'];
  };
  delete: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
      noticeCommentId: NoticeCommentId;
    };
    variables: ApiNoticeComment['delete']['params'];
  };
};

const postNoticeComment = async ({ studyId, articleId, content }: ApiNoticeComment['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiNoticeComment['post']['body']>(
    `/api/studies/${studyId}/community/${articleId}/comments`,
    {
      content,
    },
  );
  return checkType(response.data, isNull);
};
export const usePostNoticeComment = () =>
  useMutation<null, AxiosError, ApiNoticeComment['post']['variables']>(postNoticeComment);

const putNoticeComment = async ({
  studyId,
  articleId,
  noticeCommentId,
  content,
}: ApiNoticeComment['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiNoticeComment['put']['body']>(
    `/api/studies/${studyId}/community/${articleId}/comments/${noticeCommentId}`,
    {
      content,
    },
  );
  return checkType(response.data, isNull);
};
export const usePutNoticeComment = () =>
  useMutation<null, AxiosError, ApiNoticeComment['put']['variables']>(putNoticeComment);

const deleteNoticeComment = async ({
  studyId,
  articleId,
  noticeCommentId,
}: ApiNoticeComment['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>, ApiNoticeComment['delete']['variables']>(
    `/api/studies/${studyId}/community/${articleId}/comments/${noticeCommentId}`,
  );
  return checkType(response.data, isNull);
};
export const useDeleteNoticeComment = () =>
  useMutation<null, AxiosError, ApiNoticeComment['delete']['variables']>(deleteNoticeComment);
