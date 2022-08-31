import { rest } from 'msw';

import { user } from '@mocks/memberHandlers';
import studiesJSON from '@mocks/studies.json';

import type { ApiStudy } from '@api/study';

const detailStudyHandlers = [
  rest.get('/api/studies/:studyId', (req, res, ctx) => {
    const studyId = req.params.studyId;

    if (!studyId) return res(ctx.status(400), ctx.json({ message: '스터디 아이디가 없음' }));

    const study = studiesJSON.studies.find(study => String(study.id) === studyId);

    return res(ctx.status(200), ctx.json(study));
  }),
  rest.post<ApiStudy['post']['body']>('/api/studies', (req, res, ctx) => {
    const studyId = req.params.studyId;
    const { thumbnail, title, description, excerpt, enrollmentEndDate, endDate, startDate, maxMemberCount } = req.body;

    if (!studyId) return res(ctx.status(400), ctx.json({ message: '스터디 아이디가 없음' }));

    const { studies } = studiesJSON;

    const isExist = studies.some(study => study.id === Number(studyId));
    if (isExist) return res(ctx.status(400), ctx.json({ message: '이미 존재하는 스터디' }));

    studiesJSON.studies = [
      {
        id: Number(studyId),
        thumbnail,
        title,
        description,
        excerpt,
        endDate: endDate ?? '',
        enrollmentEndDate: enrollmentEndDate ?? '',
        startDate,
        maxMemberCount: maxMemberCount ?? 100,
        recruitmentStatus: 'OPEN',
        createdDate: '2022-08-18',
        currentMemberCount: 1,
        owner: user,
        members: [user],
        tags: [
          {
            id: 2,
            name: '4기',
            description: '우테코4기',
            category: {
              id: 1,
              name: 'generation',
            },
          },
          {
            id: 4,
            name: 'FE',
            description: '프론트엔드',
            category: {
              id: 2,
              name: 'area',
            },
          },
          {
            id: 5,
            name: 'React',
            description: '리액트',
            category: {
              id: 3,
              name: 'subject',
            },
          },
        ],
      },
      ...studies,
    ];

    return res(ctx.status(200));
  }),
  rest.put<ApiStudy['put']['body']>('/api/studies/:studyId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    const editedStudy = req.body;

    if (!studyId) return res(ctx.status(400), ctx.json({ message: '스터디 아이디가 없음' }));

    const { studies } = studiesJSON;

    const isExist = studies.some(study => study.id === Number(studyId));
    if (!isExist) return res(ctx.status(404), ctx.json({ message: '해당하는 스터디 없음' }));

    studiesJSON.studies = studies.map(study => {
      if (study.id === Number(studyId)) {
        return { ...study, ...editedStudy };
      }
      return study;
    });

    return res(ctx.status(200));
  }),
];

export default detailStudyHandlers;
