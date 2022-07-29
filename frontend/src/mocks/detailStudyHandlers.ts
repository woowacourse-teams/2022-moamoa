import { rest } from 'msw';

import reviewJSON from './reviews.json';
import studiesJSON from './studies.json';

const detailStudyHandlers = [
  rest.get('/api/studies/:studyId', (req, res, ctx) => {
    const studyId = req.params.studyId;

    const study = studiesJSON.studies.find(study => String(study.id) === studyId);

    return res(ctx.status(200), ctx.json(study));
  }),
];

export default detailStudyHandlers;
