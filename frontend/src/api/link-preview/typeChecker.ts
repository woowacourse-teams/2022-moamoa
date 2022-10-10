import { AxiosError } from 'axios';

import { checkType, hasOwnProperties, isObject, isString } from '@utils';

import { type ApiLinkPreview } from '@api/link-preview';

type LinkPreviewKeys = keyof ApiLinkPreview['get']['responseData'];

export const checkLinkPreview = (data: unknown): ApiLinkPreview['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`LinkPreview does not have correct type: object`);

  const keys: Array<LinkPreviewKeys> = ['title', 'description', 'imageUrl', 'domainName'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('LinkPreview does not have some properties');

  return {
    title: checkType(data.title, isString, true),
    description: checkType(data.description, isString, true),
    imageUrl: checkType(data.imageUrl, isString, true),
    domainName: checkType(data.domainName, isString, true),
  };
};
