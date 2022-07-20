import axiosInstance from '@api/axiosInstance';

export const getTagList = async () => {
  const response = await axiosInstance.get(`/api/tags`);
  console.log(response.data);
  return response.data;
};
