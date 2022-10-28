import { type AxiosError } from 'axios';
import { useInfiniteQuery } from 'react-query';

import { DEFAULT_LINK_QUERY_PARAM } from '@constants';

import type { Link, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkLinks } from '@api/links/typeChecker';

export type ApiLinks = {
  get: {
    params: {
      studyId: StudyId;
      page?: Page;
      size?: Size;
    };
    responseData: {
      links: Array<Link>;
      hasNext: boolean;
    };
    variables: ApiLinks['get']['params'];
  };
};

export type ApiInfiniteLinks = {
  get: {
    params: { studyId: StudyId };
    responseData: ApiLinks['get']['responseData'] & PageParam;
    variables: ApiInfiniteLinks['get']['params'];
  };
};

type PageParam = { page: Page };
type NextPageParam = PageParam | undefined;

const defaultParam: PageParam = {
  page: DEFAULT_LINK_QUERY_PARAM.PAGE,
};

const getLinks = async ({ studyId, page, size }: ApiLinks['get']['variables']) => {
  const response = await axiosInstance.get<ApiLinks['get']['responseData']>(
    `/api/studies/${studyId}/reference-room/links?page=${page}&size=${size}`,
  );
  return checkLinks(response.data);
};

const getLinksWithPage =
  (studyId: StudyId) =>
  async ({ pageParam = defaultParam }): Promise<ApiInfiniteLinks['get']['responseData']> => {
    const data = await getLinks({
      ...pageParam,
      studyId,
      size: DEFAULT_LINK_QUERY_PARAM.SIZE,
    });
    return { ...data, page: pageParam.page + 1 };
  };

export const QK_LINKS = 'infinite-study-links';
export const useGetInfiniteLinks = ({ studyId }: ApiInfiniteLinks['get']['variables']) => {
  return useInfiniteQuery<ApiInfiniteLinks['get']['responseData'], AxiosError>(
    [QK_LINKS, studyId],
    getLinksWithPage(studyId),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return;
        return { page: lastPage.page };
      },
    },
  );
};
