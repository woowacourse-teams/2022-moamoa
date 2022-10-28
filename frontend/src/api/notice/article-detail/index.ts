import { type AxiosError, type AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';

import { checkType, isNull } from '@utils';

import type { ArticleId, NoticeArticleDetail, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkNoticeArticle } from '@api/notice/article-detail/typeChecker';

export type ApiNoticeArticleDetail = {
  get: {
    responseData: NoticeArticleDetail;
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    variables: ApiNoticeArticleDetail['get']['params'];
  };
  post: {
    params: {
      studyId: StudyId;
    };
    body: Pick<NoticeArticleDetail, 'title' | 'content'>;
    variables: ApiNoticeArticleDetail['post']['params'] & ApiNoticeArticleDetail['post']['body'];
  };
  put: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    body: ApiNoticeArticleDetail['post']['body'];
    variables: ApiNoticeArticleDetail['put']['params'] & ApiNoticeArticleDetail['put']['body'];
  };
  delete: {
    params: {
      studyId: StudyId;
      articleId: ArticleId;
    };
    variables: ApiNoticeArticleDetail['delete']['params'];
  };
};

const getNoticeArticleDetail = async ({ studyId, articleId }: ApiNoticeArticleDetail['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiNoticeArticleDetail['get']['responseData']>(
    `/api/studies/${studyId}/notice/articles/${articleId}`,
  );
  return checkNoticeArticle(response.data);
};
export const useGetNoticeArticleDetail = ({ studyId, articleId }: ApiNoticeArticleDetail['get']['variables']) => {
  return useQuery(['get-notice-article', studyId, articleId], () => getNoticeArticleDetail({ studyId, articleId }));
};

const postNoticeArticleDetail = async ({ studyId, title, content }: ApiNoticeArticleDetail['post']['variables']) => {
  const response = await axiosInstance.post<null, AxiosResponse<null>, ApiNoticeArticleDetail['post']['body']>(
    `/api/studies/${studyId}/notice/articles`,
    {
      title,
      content,
    },
  );

  return checkType(response.data, isNull);
};
export const usePostNoticeArticleDetail = () => {
  return useMutation<null, AxiosError, ApiNoticeArticleDetail['post']['variables']>(postNoticeArticleDetail);
};

const putNoticeArticleDetail = async ({
  studyId,
  title,
  content,
  articleId,
}: ApiNoticeArticleDetail['put']['variables']) => {
  const response = await axiosInstance.put<null, AxiosResponse<null>, ApiNoticeArticleDetail['put']['body']>(
    `/api/studies/${studyId}/notice/articles/${articleId}`,
    {
      title,
      content,
    },
  );

  return checkType(response.data, isNull);
};
export const usePutNoticeArticleDetail = () => {
  return useMutation<null, AxiosError, ApiNoticeArticleDetail['put']['variables']>(putNoticeArticleDetail);
};

const deleteNoticeArticleDetail = async ({ studyId, articleId }: ApiNoticeArticleDetail['delete']['variables']) => {
  const response = await axiosInstance.delete<null, AxiosResponse<null>>(
    `/api/studies/${studyId}/notice/articles/${articleId}`,
  );

  return checkType(response.data, isNull);
};
export const useDeleteNoticeArticleDetail = () => {
  return useMutation<null, AxiosError, ApiNoticeArticleDetail['delete']['variables']>(deleteNoticeArticleDetail);
};
