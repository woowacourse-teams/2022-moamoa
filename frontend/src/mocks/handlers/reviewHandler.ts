import { rest } from 'msw';

import { user } from '@mocks/handlers/memberHandlers';
import reviewJSON from '@mocks/reviews.json';

import { type ApiReview } from '@api/review';

export const reviewHandlers = [
  rest.get('/api/studies/:studyId/reviews', (req, res, ctx) => {
    const _size = req.url.searchParams.get('size');
    const _page = req.url.searchParams.get('page');

    if (_size && _page) {
      const size = Number(_size);
      const page = _page ? Number(_page) : 0;
      const startIndex = page * size;
      const endIndexExclusive = startIndex + size;

      return res(
        ctx.status(200),
        ctx.json({
          reviews: reviewJSON.reviews.slice(startIndex, endIndexExclusive),
          totalCount: reviewJSON.reviews.length,
          hasNext: reviewJSON.reviews.length > size * page,
        }),
      );
    }

    if (_size) {
      const size = Number(_size);
      return res(
        ctx.status(200),
        ctx.json({
          reviews: reviewJSON.reviews.slice(0, size),
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

  rest.post<ApiReview['post']['body']>('/api/studies/:studyId/reviews', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

    const content = req.body['content'];
    if (!content) return res(ctx.status(400), ctx.json({ errorMessage: '리뷰가 없습니다' }));

    const review = {
      id: 1,
      member: user,
      content,
      createdDate: '2022-07-12',
      lastModifiedDate: '2022-07-12',
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

  rest.put<ApiReview['put']['body']>('/api/studies/:studyId/reviews/:reviewId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없습니다' }));

    const reviewId = Number(req.params.reviewId);
    if (!reviewId) return res(ctx.status(400), ctx.json({ errorMessage: '리뷰 아이디가 없습니다' }));

    const content = req.body['content'];
    if (!content) return res(ctx.status(400), ctx.json({ message: '내용이 없습니다.' }));

    reviewJSON.reviews = reviewJSON.reviews.map(review => {
      if (review.id === reviewId) {
        review.content = content;
      }
      return review;
    });

    return res(ctx.status(200));
  }),
];
