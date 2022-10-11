import { rest } from 'msw';

const EXPIRED_TIME = 30 * 1000;

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
    );
  }),
  rest.get('/api/auth/refresh', (req, res, ctx) => {
    const accessToken = req.headers.get('Authorization');

    if (!accessToken?.split('Bearer ').length) {
      return res(ctx.status(400), ctx.json({ message: '유효하지 않은 토큰입니다.' }));
    }

    if (accessToken.includes('12345G')) {
      return res(ctx.status(401), ctx.json({ message: '액세스 토큰 만료' }));
    }

    return res(
      ctx.status(200),
      ctx.json({
        accessToken:
          '12345GciOiJIUzI1NiJ9.eyJzdWIiOiI1NDAwMjEwNSIsImlhdCI6MTY2MDEyMDczOCwiZXhwIjoxNjYwMTI0MzM4fQ.scUIdy0iHg52NYugHLxMilgh_vbpHNdVIEwLeRRDRRk',
        expiredTime: EXPIRED_TIME,
      }),
    );
  }),
];
