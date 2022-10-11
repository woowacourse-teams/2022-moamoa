import { AxiosError } from 'axios';

import { arrayOfAll, checkType, hasOwnProperties, isObject, isString } from '@utils';

import { type ApiLinkPreview } from '@api/link-preview';

type LinkPreviewKeys = keyof ApiLinkPreview['get']['responseData'];

const arrayOfAllLinkPreviewKeys = arrayOfAll<LinkPreviewKeys>();

export const checkLinkPreview = (data: unknown): ApiLinkPreview['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`LinkPreview does not have correct type: object`);

  const keys = arrayOfAllLinkPreviewKeys(['title', 'description', 'imageUrl', 'domainName']);
  if (!hasOwnProperties(data, keys)) throw new AxiosError('LinkPreview does not have some properties');

  return {
    title: checkType(data.title, isString, true),
    description: checkType(data.description, isString, true),
    imageUrl: checkType(data.imageUrl, isString, true),
    domainName: checkType(data.domainName, isString, true),
  };
};
