import { rest } from 'msw';

import tagsJSON from './tags.json';

export const filterHandlers = [
  rest.get('/api/tags', (req, res, ctx) => {
    const tagName = req.url.searchParams.get('tag-name');

    if (!tagName) {
      return res(ctx.status(200), ctx.json({ tags: tagsJSON.tags }));
    }

    const filteredTags = tagsJSON.tags.filter(tag => tag.tagName.includes(tagName));

    return res(
      ctx.status(200),
      ctx.json({
        tags: filteredTags,
      }),
    );
  }),
];
