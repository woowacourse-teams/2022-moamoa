import axiosInstance from '@api/axiosInstance';

export const getTagList = async () => {
  const response = await axiosInstance.get(`/api/tags`);
  return response.data;
};
