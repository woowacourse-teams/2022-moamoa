import type { AxiosError } from 'axios';
import { useInfiniteQuery } from 'react-query';

import { DEFAULT_STUDY_CARD_QUERY_PARAM } from '@constants';

import type { GetStudiesRequestParams, GetStudiesResponseData, TagInfo } from '@custom-types';

import { getStudies } from '@api';

type PageParam = { page: number };

type GetStudiesResponseDataWithPage = GetStudiesResponseData & {
  page: number;
};

type UseGetInfiniteStudiesParams = Pick<GetStudiesRequestParams, 'title' | 'selectedFilters'>;

type NextPageParam = PageParam | undefined;

const defaultParam: PageParam = {
  page: DEFAULT_STUDY_CARD_QUERY_PARAM.PAGE,
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
