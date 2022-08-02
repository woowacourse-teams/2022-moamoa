import { rest } from 'msw';

import tagsJSON from '@mocks/tags.json';

export const tagHandlers = [
  rest.get('/api/tags', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(tagsJSON));
  }),
];
