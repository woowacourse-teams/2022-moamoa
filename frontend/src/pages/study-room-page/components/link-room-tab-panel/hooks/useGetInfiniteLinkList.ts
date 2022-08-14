import type { AxiosError } from 'axios';
import { useInfiniteQuery } from 'react-query';

import { DEFAULT_LINK_QUERY_PARAM } from '@constants';

import type { GetLinksResponseData, Page, StudyId } from '@custom-types';

import { getLinkList } from '@api';

type PageParam = { page: Page };
type NextPageParam = PageParam | undefined;
type GetLinkListResponseDataWithPage = GetLinksResponseData & PageParam;
type UseGetInfiniteLinkListParams = { studyId: StudyId };

const defaultParam: PageParam = {
  page: DEFAULT_LINK_QUERY_PARAM.PAGE,
};

const getLinkListWithPage =
  (studyId: StudyId) =>
  async ({ pageParam = defaultParam }): Promise<GetLinkListResponseDataWithPage> => {
    const data = await getLinkList({
      ...pageParam,
      studyId,
      size: DEFAULT_LINK_QUERY_PARAM.SIZE,
    });
    return { ...data, page: pageParam.page + 1 };
  };

export const useGetInfiniteLinkList = ({ studyId }: UseGetInfiniteLinkListParams) => {
  return useInfiniteQuery<GetLinkListResponseDataWithPage, AxiosError>(
    ['infinite-study-links', studyId],
    getLinkListWithPage(studyId),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return;
        return { page: lastPage.page };
      },
    },
  );
};
