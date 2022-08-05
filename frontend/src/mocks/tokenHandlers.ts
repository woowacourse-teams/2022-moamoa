import { rest } from 'msw';

export const tokenHandlers = [
  rest.get('/api/login/token', (req, res, ctx) => {
    const code = req.url.searchParams.get('code');

    if (!code) {
      return res(ctx.status(400), ctx.json({ message: '잘못된 요청입니다.' }));
    }
    return res(ctx.status(200), ctx.json({ token: 'asddfasdfassdf' }));
  }),
];
