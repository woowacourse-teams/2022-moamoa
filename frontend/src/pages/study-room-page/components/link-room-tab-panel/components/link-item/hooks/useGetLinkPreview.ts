import type { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import type { GetLinkPreviewRequestParams, GetLinkPreviewResponseData } from '@custom-types';

import { getLinkPreview } from '@api';

export const useGetLinkPreview = ({ linkUrl }: GetLinkPreviewRequestParams) => {
  return useQuery<GetLinkPreviewResponseData, AxiosError>(['link-preview', linkUrl], () => getLinkPreview({ linkUrl }));
};
