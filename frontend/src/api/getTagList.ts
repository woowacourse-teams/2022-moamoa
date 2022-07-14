import axiosInstance from './axiosInstance';

export const getTagList = async () => {
  const response = await axiosInstance.get(`/api/tags`);
  return response.data;
};

export const getTagListSearchedByTagName = async (tagName: string) => {
  const response = await axiosInstance.get(`/api/tags?tagName=${tagName}`);
  return response.data;
};
