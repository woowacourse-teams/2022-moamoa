import { rest } from 'msw';

const EXPIRED_TIME = 30 * 60000;
const ACCESS_TOKEN_KEY = 'accessToken';
const REFRESH_TOKEN_KEY = 'refreshToken';

export const tokenHandlers = [
  rest.post('/api/auth/login', (req, res, ctx) => {
    const code = req.url.searchParams.get('code');

    if (!code) {
      return res(ctx.status(400), ctx.json({ message: '잘못된 요청입니다.' }));
    }
    return res(
      ctx.status(200),
      ctx.cookie(REFRESH_TOKEN_KEY, 'test-refreshToken!!!sdfsdf', {
        // httpOnly: true,
        // secure: true,
      }),
      ctx.cookie(ACCESS_TOKEN_KEY, 'test-accessToken!!!sdfsdf', {
        // httpOnly: true,
        // secure: true,
      }),
    );
  }),
  rest.get('/api/auth/refresh', (req, res, ctx) => {
    if (req.cookies[REFRESH_TOKEN_KEY] === undefined)
      return res(ctx.status(400), ctx.json({ message: '리프레시 토큰 없음' }));

    if (req.cookies[REFRESH_TOKEN_KEY] !== 'test-refreshToken!!!sdfsdf')
      return res(ctx.status(401), ctx.json({ message: '리프레시 토큰 만료', code: 4001 }));

    return res(
      ctx.status(200),
      ctx.json({
        accessToken:
          'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NDAwMjEwNSIsImlhdCI6MTY2MDEyMDczOCwiZXhwIjoxNjYwMTI0MzM4fQ.scUIdy0iHg52NYugHLxMilgh_vbpHNdVIEwLeRRDRRk',
        expiredTime: EXPIRED_TIME,
      }),
      ctx.cookie(REFRESH_TOKEN_KEY, 'test-refreshToken!!!sdfsdf'),
      ctx.cookie(ACCESS_TOKEN_KEY, 'test-accessToken!!!sdfsdf'),
    );
  }),
  rest.delete('/api/auth/logout', (req, res, ctx) => {
    return res(ctx.status(200), ctx.cookie(REFRESH_TOKEN_KEY, 'no'), ctx.cookie(ACCESS_TOKEN_KEY, ''));
  }),
  rest.get('/api/auth/login/status', (req, res, ctx) => {
    const hasAccessToken = !!req.cookies[ACCESS_TOKEN_KEY].split(',')[0];

    if (!hasAccessToken) return res(ctx.status(200), ctx.json({ isLoggedIn: false }));

    // if (isInitialRequest) {
    //   isInitialRequest = false;
    //   return res(ctx.status(401), ctx.json({ message: 'accessToken 만료' }));
    // }
    return res(ctx.status(200), ctx.json({ isLoggedIn: true }));
  }),
];

// let isInitialRequest = true;
