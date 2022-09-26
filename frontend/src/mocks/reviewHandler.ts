import { rest } from 'msw';

import { user } from '@mocks/memberHandlers';
import reviewJSON from '@mocks/reviews.json';

import { isObject } from '@utils';

export const reviewHandlers = [
  rest.get('/api/studies/:studyId/reviews', (req, res, ctx) => {
    const size = req.url.searchParams.get('size');
    if (size) {
      const sizeNum = Number(size);
      return res(
        ctx.status(200),
        ctx.json({
          reviews: reviewJSON.reviews.slice(0, sizeNum),
          totalCount: reviewJSON.reviews.length,
        }),
      );
    }
    return res(
      ctx.status(200),
      ctx.json({
        reviews: reviewJSON.reviews,
        totalCount: reviewJSON.reviews.length,
      }),
    );
  }),

  rest.post('/api/studies/:studyId/reviews', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가' }));

    const content = isObject(req.body) ? req.body['content'] : null;
    if (!content) return res(ctx.status(400), ctx.json({ errorMessage: '리뷰가 없습니다' }));

    const review = {
      id: 1,
      member: user,
      content,
      createdDate: '2022-07-12',
      lastModifiedDate: '',
    };
    const reviews = [review, ...reviewJSON.reviews];
    reviewJSON.reviews = reviews;

    return res(ctx.status(200));
  }),

  rest.delete('/api/studies/:studyId/reviews/:reviewId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없습니다' }));

    const reviewId = Number(req.params.reviewId);
    if (!reviewId) return res(ctx.status(400), ctx.json({ errorMessage: '리뷰 아이디가 없습니다' }));

    reviewJSON.reviews = reviewJSON.reviews.filter(({ id }) => reviewId !== id);

    return res(ctx.status(200));
  }),

  rest.put('/api/studies/:studyId/reviews/:reviewId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없습니다' }));

    const reviewId = Number(req.params.reviewId);
    if (!reviewId) return res(ctx.status(400), ctx.json({ errorMessage: '리뷰 아이디가 없습니다' }));

    const content = isObject(req.body) ? req.body['content'] : null;

    reviewJSON.reviews = reviewJSON.reviews.map(review => {
      if (review.id === reviewId) {
        review.content = content;
      }
      return review;
    });

    return res(ctx.status(200));
  }),
];
