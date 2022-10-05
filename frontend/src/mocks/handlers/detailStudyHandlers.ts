import { rest } from 'msw';

import { user } from '@mocks/handlers/memberHandlers';
import studiesJSON from '@mocks/studies.json';

import { ApiStudies } from '@api/studies';
import type { ApiStudy } from '@api/study';

const detailStudyHandlers = [
  rest.get('/api/studies/:studyId', (req, res, ctx) => {
    const studyId = req.params.studyId;

    if (!studyId) return res(ctx.status(400), ctx.json({ message: '스터디 아이디가 없음' }));

    const study = studiesJSON.studies.find(study => String(study.id) === studyId);

    return res(ctx.status(200), ctx.json(study));
  }),
  rest.post<ApiStudy['post']['body']>('/api/studies', (req, res, ctx) => {
    const { thumbnail, title, description, excerpt, enrollmentEndDate, endDate, startDate, maxMemberCount } = req.body;

    const { studies } = studiesJSON as Pick<ApiStudies['get']['responseData'], 'studies'>;

    // TODO: json 파일의 타입을 지정할 순 없을까?
    studiesJSON.studies = [
      {
        id: 1000001,
        thumbnail,
        title,
        description,
        excerpt,
        endDate: endDate ?? null,
        enrollmentEndDate: enrollmentEndDate ?? null,
        startDate,
        maxMemberCount: maxMemberCount ?? null,
        recruitmentStatus: 'RECRUITMENT_START',
        createdDate: '2022-08-18',
        currentMemberCount: 1,
        owner: user,
        members: [],
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
