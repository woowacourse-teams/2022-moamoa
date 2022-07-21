import axiosInstance from '@api/axiosInstance';

export const getAccessToken = async (code: string) => {
  const response = await axiosInstance.post(`/api/login/token?code=${code}`);
  return response.data;
};
