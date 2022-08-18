import { rest } from 'msw';

import myStudiesJson from '@mocks/my-studies.json';

export const myHandlers = [
  rest.get('/api/my/studies', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(myStudiesJson));
  }),
];
