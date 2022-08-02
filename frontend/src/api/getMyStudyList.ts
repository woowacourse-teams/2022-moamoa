import axiosInstance from '@api/axiosInstance';

export const getMyStudyList = async () => {
  const response = await axiosInstance.get(`/api/my/studies`);
  return response.data;
};
