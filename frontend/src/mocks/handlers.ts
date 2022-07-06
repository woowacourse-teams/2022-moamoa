import { rest } from 'msw';

import studyCardJSON from './study-cards.json';

export const handlers = [
  rest.get('/api/studies', (req, res, ctx) => {
    const page = req.url.searchParams.get('page');
    const size = req.url.searchParams.get('size');

    const sizeNum = Number(size);
    const pageNum = Number(page);
    const startIndex = pageNum * sizeNum;
    const endIndex = startIndex + sizeNum;

    if (!page && !size) {
      return res(
        ctx.status(200),
        ctx.json({
          studies: studyCardJSON.studies.slice(0, 5),
          hasNext: true,
        }),
      );
    }

    if (!page || !size) {
      return res(ctx.status(400), ctx.json({ message: '잘못된 요청입니다.' }));
    }

    return res(
      ctx.status(200),
      ctx.json({
        studies: studyCardJSON.studies.slice(startIndex, endIndex),
        hasNext: endIndex <= studyCardJSON.studies.length,
      }),
    );
  }),
];
