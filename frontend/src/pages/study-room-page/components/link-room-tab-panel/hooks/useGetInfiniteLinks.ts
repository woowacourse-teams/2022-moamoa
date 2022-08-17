import type { AxiosError } from 'axios';
import { useInfiniteQuery } from 'react-query';

import { DEFAULT_LINK_QUERY_PARAM } from '@constants';

import type { GetLinksResponseData, Page, StudyId } from '@custom-types';

import { getLinks } from '@api';

type PageParam = { page: Page };
type NextPageParam = PageParam | undefined;
type GetLinksResponseDataWithPage = GetLinksResponseData & PageParam;
type UseGetInfiniteLinksParams = { studyId: StudyId };

const defaultParam: PageParam = {
  page: DEFAULT_LINK_QUERY_PARAM.PAGE,
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

export const QK_FETCH_LINKS = 'infinite-study-links';
export const useGetInfiniteLinks = ({ studyId }: UseGetInfiniteLinksParams) => {
  return useInfiniteQuery<GetLinksResponseDataWithPage, AxiosError>(
    [QK_FETCH_LINKS, studyId],
    getLinksWithPage(studyId),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return;
        return { page: lastPage.page };
      },
    },
  );
};
