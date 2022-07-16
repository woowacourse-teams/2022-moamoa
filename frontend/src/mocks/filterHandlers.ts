import { rest } from 'msw';

import filtersJSON from './filters.json';

export const filterHandlers = [
  rest.get('/api/filters', (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        filters: filtersJSON.filters,
      }),
    );
  }),
];
