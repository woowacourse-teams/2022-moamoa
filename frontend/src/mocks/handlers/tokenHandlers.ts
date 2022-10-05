import { rest } from 'msw';

const EXPIRED_TIME = 30 * 60000;

export const tokenHandlers = [
  rest.post('/api/auth/login', (req, res, ctx) => {
    const code = req.url.searchParams.get('code');

    if (!code) {
      return res(ctx.status(400), ctx.json({ message: '잘못된 요청입니다.' }));
    }
    return res(
      ctx.status(200),
      ctx.json({
        accessToken:
          'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NDAwMjEwNSIsImlhdCI6MTY2MDEyMDczOCwiZXhwIjoxNjYwMTI0MzM4fQ.scUIdy0iHg52NYugHLxMilgh_vbpHNdVIEwLeRRDRRk',
        expiredTime: EXPIRED_TIME,
      }),
      ctx.cookie('refreshToken', 'test-refreshToken!!!sdfsdf', {
        // httpOnly: true,
        // secure: true,
      }),
    );
  }),
  rest.get('/api/auth/refresh', (req, res, ctx) => {
    if (!req.cookies.refreshToken) return res(ctx.status(400), ctx.json({ message: '리프레시 토큰 없음' }));

    // return res(ctx.status(401), ctx.json({ message: '리프레시 토큰 만료', code: 4001 }));

    return res(
      ctx.status(200),
      ctx.json({
        accessToken:
          '1234eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NDAwMjEwNSIsImlhdCI6MTY2MDEyMDczOCwiZXhwIjoxNjYwMTI0MzM4fQ.scUIdy0iHg52NYugHLxMilgh_vbpHNdVIEwLeRRDRRk',
        expiredTime: EXPIRED_TIME,
      }),
      ctx.cookie('refreshToken', 'test-refreshToken!!!sdfsdf', {
        // httpOnly: true,
        // secure: true,
      }),
    );
  }),
  rest.delete('/api/auth/logout', (req, res, ctx) => {
    return res(ctx.status(200), ctx.cookie('refreshToken', ''));
  }),
];
