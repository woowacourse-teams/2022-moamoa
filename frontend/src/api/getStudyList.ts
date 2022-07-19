import { DEFAULT_STUDY_CARD_QUERY_PARAM } from '@constants';

import axiosInstance from '@api/axiosInstance';

import type { FilterInfo } from '@pages/main-page/filter-section/FilterSection';

const { PAGE, SIZE } = DEFAULT_STUDY_CARD_QUERY_PARAM;
export const getStudyList = async (page = PAGE, size = SIZE, title: string, selectedFilters: Array<FilterInfo>) => {
  const filterParams = selectedFilters.map(({ id, categoryName }) => `&${categoryName}=${id}`).join('');
  const titleParams = title && `&title=${title}`;

  const response = await axiosInstance.get(
    `/api/studies/search?page=${page}&size=${size}${titleParams}${filterParams}`,
  );
  return response.data;
};
