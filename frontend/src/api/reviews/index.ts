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
    };
    responseData: {
      reviews: Array<StudyReview>;
      totalCount: number;
    };
    variables: ApiStudyReviews['get']['params'];
  };
};

export type ApiInfiniteStudyReviews = {
  get: {
    params: Merge<ApiStudyReviews['get']['params'], { size?: Size }>;
    responseData: Merge<ApiStudyReviews['get']['responseData'], { hasNext: boolean; totalCount: number }>;
    variables: ApiInfiniteStudyReviews['get']['params'];
  };
};

type PageParam = { page: number };
type NextPageParam = PageParam | undefined;

const { PAGE, SIZE } = DEFAULT_STUDY_REVIEW_QUERY_PARAM;
const defaultParam: PageParam = {
  page: PAGE,
};

// size와 page가 없는 경우에는 전체를 불러온다
export const getStudyReviews = async ({ studyId, size }: ApiStudyReviews['get']['variables']) => {
  const url = size ? `/api/studies/${studyId}/reviews?size=${size}` : `/api/studies/${studyId}/reviews`;
  const response = await axiosInstance.get<ApiStudyReviews['get']['responseData']>(url);
  return checkStudyReviews(response.data);
};
export const useGetStudyReviews = ({ size, studyId }: ApiStudyReviews['get']['variables']) => {
  return useQuery([QK_STUDY_REVIEWS, size, studyId], () => getStudyReviews({ size, studyId }));
};

export const getStudyReviewsWithPage =
  ({ size = SIZE, studyId }: ApiInfiniteStudyReviews['get']['variables']) =>
  async ({
    pageParam = defaultParam,
  }): Promise<Merge<ApiInfiniteStudyReviews['get']['responseData'], { page: Page }>> => {
    const url = `/api/studies/${studyId}/reviews?size=${size}&page=${pageParam.page}`;
    const response = await axiosInstance.get<ApiInfiniteStudyReviews['get']['responseData']>(url);
    return { ...response.data, page: pageParam.page };
  };

export const useGetInfiniteStudyReviews = ({ size, studyId }: ApiInfiniteStudyReviews['get']['variables']) => {
  return useInfiniteQuery<Merge<ApiInfiniteStudyReviews['get']['responseData'], { page: Page }>, AxiosError>(
    [QK_STUDY_REVIEWS_INFINITE_SCROLL, studyId],
    getStudyReviewsWithPage({ size, studyId }),
    {
      getNextPageParam: (lastPage): NextPageParam => {
        if (!lastPage.hasNext) return;
        return { page: lastPage.page + 1 };
      },
    },
  );
};
