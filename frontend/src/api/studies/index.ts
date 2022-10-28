import { type AxiosError } from 'axios';
import { useInfiniteQuery } from 'react-query';

import { DEFAULT_STUDY_CARD_QUERY_PARAM } from '@constants';

import buildURLQuery from '@utils/buildURLQuery';

import type { Page, Size, Study, TagInfo } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkStudies } from '@api/studies/typeChecker';

export type ApiStudies = {
  get: {
    params: {
      page?: Page;
      size?: Size;
      title: string;
      selectedFilters: Array<TagInfo>;
    };
    responseData: {
      studies: Array<Study>;
      hasNext: boolean;
    };
    variables: ApiStudies['get']['params'];
  };
};

export type ApiInfiniteStudies = {
  get: {
    params: Pick<ApiStudies['get']['params'], 'title' | 'selectedFilters'>;
    responseData: ApiStudies['get']['responseData'] & { page: number };
    variables: ApiInfiniteStudies['get']['params'];
  };
};

// get
type PageParam = { page: number };
type NextPageParam = PageParam | undefined;

const { PAGE, SIZE } = DEFAULT_STUDY_CARD_QUERY_PARAM;
const defaultParam: PageParam = {
  page: DEFAULT_STUDY_CARD_QUERY_PARAM.PAGE,
};

const getStudies = async ({ page = PAGE, size = SIZE, title, selectedFilters }: ApiStudies['get']['variables']) => {
  const tagParams = selectedFilters.reduce((acc, { id, categoryName }) => ({ ...acc, [categoryName]: id }), {});
  const url = buildURLQuery('/api/studies/search', {
    page,
    size,
    title,
    ...tagParams,
  });
  const response = await axiosInstance.get<ApiStudies['get']['responseData']>(url);
  return checkStudies(response.data);
};

const getStudiesWithPage =
  ({ title, selectedFilters }: ApiInfiniteStudies['get']['variables']) =>
  async ({ pageParam = defaultParam }): Promise<ApiInfiniteStudies['get']['responseData']> => {
    const data = await getStudies({
      ...pageParam,
      title,
      selectedFilters,
      size: DEFAULT_STUDY_CARD_QUERY_PARAM.SIZE,
    });

    return { ...data, page: pageParam.page + 1 };
  };

export const useGetInfiniteStudies = ({ title, selectedFilters }: ApiInfiniteStudies['get']['variables']) => {
  return useInfiniteQuery<ApiInfiniteStudies['get']['responseData'], AxiosError>(
    ['infinite-scroll-searched-study-list', title, selectedFilters],
    getStudiesWithPage({ title, selectedFilters }),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return;
        return { page: lastPage.page };
      },
    },
  );
};
