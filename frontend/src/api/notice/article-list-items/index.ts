import { type AxiosError } from 'axios';
import { useQuery } from 'react-query';

import buildURLQuery from '@utils/buildURLQuery';

import type { NoticeArticleDetail, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkNoticeArticleListItems } from '@api/notice/article-list-items/typeChecker';

export type ApiNoticeArticleListItems = {
  get: {
    responseData: {
      articles: Array<Omit<NoticeArticleDetail, 'content'>>;
      currentPage: number;
      lastPage: number;
      totalCount: number;
    };
    params: {
      studyId: StudyId;
      page?: Page;
      size?: Size;
    };
    variables: ApiNoticeArticleListItems['get']['params'];
  };
};

const getNoticeArticleListItems = async ({
  studyId,
  page = 1,
  size = 8,
}: ApiNoticeArticleListItems['get']['variables']) => {
  // 서버쪽에서는 page를 0번부터 계산하기 때문에 page - 1을 해줘야 한다
  const response = await axiosInstance.get<ApiNoticeArticleListItems['get']['responseData']>(
    buildURLQuery(`/api/studies/${studyId}/notice/articles`, { page: page - 1, size }),
  );

  return checkNoticeArticleListItems(response.data);
};

export const useGetNoticeArticleListItems = ({ studyId, page }: ApiNoticeArticleListItems['get']['variables']) => {
  return useQuery<ApiNoticeArticleListItems['get']['responseData'], AxiosError>(
    ['get-notice-articles', studyId, page],
    () => getNoticeArticleListItems({ studyId, page }),
  );
};
