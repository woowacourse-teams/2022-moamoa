import { rest } from 'msw';

export const tokenHandlers = [
  rest.post('/api/login/token', (req, res, ctx) => {
    const code = req.url.searchParams.get('code');

    if (!code) {
      return res(ctx.status(400), ctx.json({ message: '잘못된 요청입니다.' }));
    }
    return res(ctx.status(200), ctx.json({ accessToken: 'asddfasdfassdf' }));
  }),
  rest.get('/api/auth/refresh', (req, res, ctx) => {
    if (req.cookies.refreshToken === 'test-refreshToken!!!sdfsdf')
      return res(ctx.status(401), ctx.json({ message: '리프레시 토큰 만료' }), ctx.cookie('refreshToken', ''));

    return res(
      ctx.status(200),
      ctx.json({ accessToken: '12341234241234' }),
      ctx.cookie('refreshToken', 'test-refreshToken!!!sdfsdf', {
        // httpOnly: true,
        // secure: true,
      }),
    );
  }),
];
