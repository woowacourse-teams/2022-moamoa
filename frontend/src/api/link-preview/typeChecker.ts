import { AxiosError } from 'axios';

import { checkType, hasOwnProperties, isNull, isObject, isString } from '@utils';

import { type ApiLinkPreview } from '@api/link-preview';

type LinkPreviewKeys = keyof ApiLinkPreview['get']['responseData'];

export const checkLinkPreview = (data: unknown): ApiLinkPreview['get']['responseData'] => {
  if (!isObject(data)) throw new AxiosError(`LinkPreview does not have correct type: object`);

  const keys: Array<LinkPreviewKeys> = ['title', 'description', 'imageUrl', 'domainName'];
  if (!hasOwnProperties(data, keys)) throw new AxiosError('LinkPreview does not have some properties');

  const isStringOrNull = (value: unknown): value is string | null => isString(value) || isNull(value);

  return {
    title: checkType(data.title, isStringOrNull),
    description: checkType(data.description, isStringOrNull),
    imageUrl: checkType(data.imageUrl, isStringOrNull),
    domainName: checkType(data.domainName, isStringOrNull),
  };
};
