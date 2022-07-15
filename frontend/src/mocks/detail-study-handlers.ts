import { rest } from 'msw';

import reviewJSON from './reviews.json';
import studiesJSON from './studies.json';

const detailStudyHandlers = [
  rest.get('/api/study', (req, res, ctx) => {
    const studyId = req.url.searchParams.get('study-id');

    const study = studiesJSON.studies.find(study => String(study.id) === studyId);

    return res(
      ctx.status(200),
      ctx.json({
        study,
      }),
    );
  }),

  rest.get('/api/studies/:studyId/review', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(reviewJSON));
  }),
];

export default detailStudyHandlers;
