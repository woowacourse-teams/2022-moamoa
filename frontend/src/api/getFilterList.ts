import axiosInstance from '@api/axiosInstance';

export const getFilterList = async () => {
  const response = await axiosInstance.get(`/api/filters`);
  return response.data;
};
