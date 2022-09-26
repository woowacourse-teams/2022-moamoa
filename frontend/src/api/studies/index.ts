import type { AxiosError } from 'axios';
import { useInfiniteQuery } from 'react-query';

import { DEFAULT_STUDY_CARD_QUERY_PARAM } from '@constants';

import type { Page, Size, Study, TagInfo } from '@custom-types';

import axiosInstance from '@api/axiosInstance';

// get
export type GetStudiesRequestParams = {
  page?: Page;
  size?: Size;
  title: string;
  selectedFilters: Array<TagInfo>;
};
export type GetStudiesResponseData = {
  studies: Array<Study>;
  hasNext: boolean;
};
type PageParam = { page: number };
type NextPageParam = PageParam | undefined;
type GetStudiesResponseDataWithPage = GetStudiesResponseData & {
  page: number;
};
type UseGetInfiniteStudiesParams = Pick<GetStudiesRequestParams, 'title' | 'selectedFilters'>;

const { PAGE, SIZE } = DEFAULT_STUDY_CARD_QUERY_PARAM;
const defaultParam: PageParam = {
  page: DEFAULT_STUDY_CARD_QUERY_PARAM.PAGE,
};

export const getStudies = async ({ page = PAGE, size = SIZE, title, selectedFilters }: GetStudiesRequestParams) => {
  const tagParams = selectedFilters.map(({ id, categoryName }) => `&${categoryName}=${id}`).join('');
  const titleParams = title && `&title=${title}`;

  const response = await axiosInstance.get<GetStudiesResponseData>(
    `/api/studies/search?page=${page}&size=${size}${titleParams}${tagParams}`,
  );
  return response.data;
};

const getStudiesWithPage =
  (title: string, selectedFilters: Array<TagInfo>) =>
  async ({ pageParam = defaultParam }): Promise<GetStudiesResponseDataWithPage> => {
    const data = await getStudies({
      ...pageParam,
      title,
      selectedFilters,
      size: DEFAULT_STUDY_CARD_QUERY_PARAM.SIZE,
    });
    return { ...data, page: pageParam.page + 1 };
  };

export const useGetInfiniteStudies = ({ title, selectedFilters }: UseGetInfiniteStudiesParams) => {
  return useInfiniteQuery<GetStudiesResponseDataWithPage, AxiosError>(
    ['infinite-scroll-searched-study-list', title, selectedFilters],
    getStudiesWithPage(title, selectedFilters),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return;
        return { page: lastPage.page };
      },
    },
  );
};
