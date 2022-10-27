import { type AxiosError } from 'axios';
import { useInfiniteQuery, useQuery } from 'react-query';

import { DEFAULT_STUDY_REVIEW_QUERY_PARAM } from '@constants';

import type { Merge, Page, Size, StudyId, StudyReview } from '@custom-types';

import axiosInstance from '@api/axiosInstance';
import { checkStudyReviews } from '@api/reviews/typeChecker';

export const QK_STUDY_REVIEWS = 'study-reviews';
export const QK_STUDY_REVIEWS_INFINITE_SCROLL = 'infinite-scroll-study-reviews';

export type ApiStudyReviews = {
  get: {
    params: {
      studyId: StudyId;
      size?: Size;
      page?: Page;
    };
    responseData: {
      reviews: Array<StudyReview>;
      totalCount: number;
      // page의 유무에 따라 hasNext의 존재 유무가 결정된다
      // TODO: review가 detail페이지에서 한번에 불러오고 있기 때문에 이렇게 hasNext가 optional인데
      // modal로 UI를 바꿔서 페이지네이션 처리를 해야한다.
      hasNext?: boolean;
    };
    variables: ApiStudyReviews['get']['params'];
  };
};

type PageParam = { page: number };
type NextPageParam = PageParam | undefined;

const { PAGE, SIZE } = DEFAULT_STUDY_REVIEW_QUERY_PARAM;
const defaultParam: PageParam = {
  page: PAGE,
};

// size와 page가 없는 경우에는 전체를 불러온다
export const getStudyReviews = async ({ studyId, size, page }: ApiStudyReviews['get']['variables']) => {
  let url = `/api/studies/${studyId}/reviews`;
  if (size) {
    url = `/api/studies/${studyId}/reviews?size=${size}`;
    if (page) `/api/studies/${studyId}/reviews?size=${size}&page=${page}`;
  }
  const response = await axiosInstance.get<ApiStudyReviews['get']['responseData']>(url);
  return checkStudyReviews(response.data);
};
export const useGetStudyReviews = ({ size, studyId, page }: ApiStudyReviews['get']['variables']) => {
  return useQuery([QK_STUDY_REVIEWS, size, studyId], () => getStudyReviews({ size, studyId, page }));
};

export const getStudyReviewsWithPage =
  ({ size = SIZE, studyId, page }: ApiStudyReviews['get']['variables']) =>
  async ({ pageParam = defaultParam }): Promise<Merge<ApiStudyReviews['get']['responseData'], { page: Page }>> => {
    const data = await getStudyReviews({ studyId, size, page });
    return { ...data, page: pageParam.page };
  };

export const useGetInfiniteStudyReviews = ({ size, studyId, page }: ApiStudyReviews['get']['variables']) => {
  return useInfiniteQuery<Merge<ApiStudyReviews['get']['responseData'], { page: Page; hasNext?: boolean }>, AxiosError>(
    [QK_STUDY_REVIEWS_INFINITE_SCROLL, studyId],
    getStudyReviewsWithPage({ size, studyId, page }),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return; // 운좋게 undefined와 false를 같이 검사했다
        return { page: lastPage.page + 1 };
      },
    },
  );
};
