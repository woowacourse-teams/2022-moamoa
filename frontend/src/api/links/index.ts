import type { AxiosError } from 'axios';
import { useInfiniteQuery } from 'react-query';

import { DEFAULT_LINK_QUERY_PARAM } from '@constants';

import type { Link, Page, Size, StudyId } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

// get
export type GetLinksRequestParams = {
  studyId: StudyId;
  page?: Page;
  size?: Size;
};
export type GetLinksResponseData = {
  links: Array<Link>;
  hasNext: boolean;
};
type PageParam = { page: Page };
type NextPageParam = PageParam | undefined;
type GetLinksResponseDataWithPage = GetLinksResponseData & PageParam;
type UseGetInfiniteLinksParams = { studyId: StudyId };

const defaultParam: PageParam = {
  page: DEFAULT_LINK_QUERY_PARAM.PAGE,
};

export const getLinks = async ({ studyId, page, size }: GetLinksRequestParams) => {
  const response = await axiosInstance.get<GetLinksResponseData>(
    `/api/studies/${studyId}/reference-room/links?page=${page}&size=${size}`,
  );
  return response.data;
};

const getLinksWithPage =
  (studyId: StudyId) =>
  async ({ pageParam = defaultParam }): Promise<GetLinksResponseDataWithPage> => {
    const data = await getLinks({
      ...pageParam,
      studyId,
      size: DEFAULT_LINK_QUERY_PARAM.SIZE,
    });
    return { ...data, page: pageParam.page + 1 };
  };

export const QK_LINKS = 'infinite-study-links';
export const useGetInfiniteLinks = ({ studyId }: UseGetInfiniteLinksParams) => {
  return useInfiniteQuery<GetLinksResponseDataWithPage, AxiosError>([QK_LINKS, studyId], getLinksWithPage(studyId), {
    getNextPageParam: (lastPage): NextPageParam => {
      if (!lastPage.hasNext) return;
      return { page: lastPage.page };
    },
  });
};
