import { rest } from 'msw';

export const tokenHandlers = [
  rest.post('/api/auth/login/token', (req, res, ctx) => {
    const code = req.url.searchParams.get('code');

    if (!code) {
      return res(ctx.status(400), ctx.json({ message: '잘못된 요청입니다.' }));
    }
    return res(
      ctx.status(200),
      ctx.json({ accessToken: 'asddfasdfassdf' }),
      ctx.cookie('refreshToken', 'test-refreshToken!!!sdfsdf', {
        // httpOnly: true,
        // secure: true,
      }),
    );
  }),
  rest.get('/api/auth/refresh', (req, res, ctx) => {
    if (!req.cookies.refreshToken) return res(ctx.status(401), ctx.json({ message: '리프레시 토큰 없음' }));

    return res(
      ctx.status(200),
      ctx.json({ accessToken: '12341234241234' }),
      ctx.cookie('refreshToken', 'test-refreshToken!!!sdfsdf', {
        // httpOnly: true,
        // secure: true,
      }),
    );
  }),
  rest.delete('/api/auth/logout', (req, res, ctx) => {
    const accessTokenString = req.headers.get('Authorization');
    console.log(accessTokenString);
    if (!accessTokenString) {
      return res(ctx.status(400), ctx.json({ message: '토큰 없음' }));
    }
    return res(ctx.status(200), ctx.cookie('refreshToken', ''));
  }),
];
