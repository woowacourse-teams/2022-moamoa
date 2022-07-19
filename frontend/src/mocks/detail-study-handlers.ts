import { rest } from 'msw';

import reviewJSON from './reviews.json';
import studiesJSON from './studies.json';

const detailStudyHandlers = [
  rest.get('/api/studies', (req, res, ctx) => {
    const studyId = req.url.searchParams.get('study-id');

    const study = studiesJSON.studies.find(study => String(study.id) === studyId);
    return res(
      ctx.status(200),
      ctx.json({
        study,
      }),
    );
  }),

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
];

export default detailStudyHandlers;
