import { rest } from 'msw';

import reviewJSON from '@mocks/reviews.json';

import isObject from '@utils/isObject';

export const reviewHandlers = [
  rest.get('/api/studies/:studyId/reviews', (req, res, ctx) => {
    const size = req.url.searchParams.get('size');
    if (size) {
      const sizeNum = Number(size);
      return res(
        ctx.status(200),
        ctx.json({
          reviews: reviewJSON.reviews.slice(0, sizeNum),
          totalResults: reviewJSON.reviews.length,
        }),
      );
    }
    return res(
      ctx.status(200),
      ctx.json({
        reviews: reviewJSON.reviews,
        totalResults: reviewJSON.reviews.length,
      }),
    );
  }),

  rest.post('/api/studies/:studyId/reviews', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가' }));

    const content = isObject(req.body) ? req.body['content'] : null;
    if (!content) return res(ctx.status(400), ctx.json({ errorMessage: '리뷰가 없습니다' }));

    const reviews = structuredClone(reviewJSON.reviews);
    reviews.push({
      id: 1,
      member: {
        id: 17899306,
        username: 'DsWB',
        imageUrl: 'https://picsum.photos/id/153/200/200',
        profileUrl: 'https://github.com/airman5573',
      },
      content,
      createdDate: '2022-07-12',
      lastModifiedDate: '',
    });
    return res(ctx.status(200), ctx.json({ reviews }));
  }),
];
