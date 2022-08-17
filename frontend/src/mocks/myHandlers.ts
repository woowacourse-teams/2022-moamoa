import { rest } from 'msw';

import myStudiesJson from '@mocks/my-studies.json';

export const myHandlers = [
  rest.get('/api/my/studies', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(myStudiesJson));
  }),
  rest.post('/api/studies/:studyId/members', (req, res, ctx) => {
    // join study
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ message: '스터디 아이디 없음' }));

    return res(ctx.status(200));
  }),
  rest.delete('/api/studies/:studyId/members', (req, res, ctx) => {
    // quit study
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ message: '스터디 아이디 없음' }));

    const { studies } = myStudiesJson;
    myStudiesJson.studies = studies.filter(study => study.id !== Number(studyId));
    return res(ctx.status(200));
  }),
];
