import axiosInstance from '@api/axiosInstance';

export const getAccessToken = async (code: string) => {
  const response = await axiosInstance.get(`/api/login/token?code=${code}`);
  return response.data;
};
