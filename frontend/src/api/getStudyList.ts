import { DEFAULT_STUDY_CARD_QUERY_PARAM } from '@constants';

import axiosInstance from './axiosInstance';

const { PAGE, SIZE } = DEFAULT_STUDY_CARD_QUERY_PARAM;
export const getStudyList = async (page = PAGE, size = SIZE) => {
  const response = await axiosInstance.get(`/api/studies?page=${page}&size=${size}`);
  return response.data;
};
