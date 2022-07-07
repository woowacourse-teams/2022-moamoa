import { DEFAULT_STUDY_CARD_QUERY_PARAM } from '@constants';

import axiosInstance from '@api/axiosInstance';

const { PAGE, SIZE } = DEFAULT_STUDY_CARD_QUERY_PARAM;
export const getStudyListSearchedByTitle = async (page = PAGE, size = SIZE, title: string) => {
  const response = await axiosInstance.get(`/api/studies/search?page=${page}&size=${size}&title=${title}`);
  return response.data;
};
