import type { AxiosError } from 'axios';
import { useInfiniteQuery } from 'react-query';

import { DEFAULT_STUDY_CARD_QUERY_PARAM } from '@constants';

import type { GetStudyListRequestParams, GetStudyListResponseData, TagInfo } from '@custom-types';

import { getStudyList } from '@api';

type PageParam = { page: number };

type GetStudyListResponseDataWithPage = GetStudyListResponseData & {
  page: number;
};

type UseGetInfiniteStudyListParams = Pick<GetStudyListRequestParams, 'title' | 'selectedFilters'>;

type NextPageParam = PageParam | undefined;

const defaultParam: PageParam = {
  page: DEFAULT_STUDY_CARD_QUERY_PARAM.PAGE,
};

const getStudyListWithPage =
  (title: string, selectedFilters: Array<TagInfo>) =>
  async ({ pageParam = defaultParam }): Promise<GetStudyListResponseDataWithPage> => {
    const data = await getStudyList({
      ...pageParam,
      title,
      selectedFilters,
      size: DEFAULT_STUDY_CARD_QUERY_PARAM.SIZE,
    });
    return { ...data, page: pageParam.page + 1 };
  };

const useGetInfiniteStudyList = ({ title, selectedFilters }: UseGetInfiniteStudyListParams) => {
  return useInfiniteQuery<GetStudyListResponseDataWithPage, AxiosError>(
    ['infinite-scroll-searched-study-list', title, selectedFilters],
    getStudyListWithPage(title, selectedFilters),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return;
        return { page: lastPage.page };
      },
    },
  );
};

export default useGetInfiniteStudyList;
