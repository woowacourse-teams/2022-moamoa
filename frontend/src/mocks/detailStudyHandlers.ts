import { rest } from 'msw';

import reviewJSON from '@mocks/reviews.json';
import studiesJSON from '@mocks/studies.json';

const detailStudyHandlers = [
  rest.get('/api/studies/:studyId', (req, res, ctx) => {
    const studyId = req.params.studyId;

    if (!studyId) return res(ctx.status(400), ctx.json({ message: '스터디 아이디가 없음' }));

    const study = studiesJSON.studies.find(study => String(study.id) === studyId);

    return res(ctx.status(200), ctx.json(study));
  }),
  rest.post('/api/studies/:studyId', (req, res, ctx) => {
    // const studyId = req.params.studyId;

    return res(ctx.status(200));
  }),
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
];

export default detailStudyHandlers;
