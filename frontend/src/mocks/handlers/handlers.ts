import { rest } from 'msw';

import { communityHandlers } from '@mocks/handlers/communityHandler';
import detailStudyHandlers from '@mocks/handlers/detailStudyHandlers';
import { linkHandlers } from '@mocks/handlers/linkHandlers';
import { memberHandlers } from '@mocks/handlers/memberHandlers';
import { myHandlers } from '@mocks/handlers/myHandlers';
import { noticeHandlers } from '@mocks/handlers/noticeHandler';
import { reviewHandlers } from '@mocks/handlers/reviewHandler';
import { tagHandlers } from '@mocks/handlers/tagHandlers';
import { tokenHandlers } from '@mocks/handlers/tokenHandlers';
import studyJSON from '@mocks/studies.json';

export const handlers = [
  rest.get('/api/studies/search', (req, res, ctx) => {
    const title = req.url.searchParams.get('title');
    const page = req.url.searchParams.get('page');
    const size = req.url.searchParams.get('size');

    // const generations = req.url.searchParams.getAll('generation');
    // const areas = req.url.searchParams.getAll('area');
    // const tags = req.url.searchParams.getAll('tag');

    if ((size === null && page !== null) || (size !== null && page === null)) {
      return res(ctx.status(400), ctx.json({ message: 'size혹은 page가 없습니다' }));
    }

    const sizeNum = Number(size);
    const pageNum = Number(page);
    const startIndex = pageNum * sizeNum;
    const endIndexExclusive = startIndex + sizeNum;

    if (!title) {
      return res(
        ctx.status(200),
        ctx.json({
          studies: studyJSON.studies.slice(startIndex, endIndexExclusive),
          hasNext: endIndexExclusive < studyJSON.studies.length,
        }),
      );
    }

    const searchedStudies = studyJSON.studies.filter(study => study.title.includes(title));

    if (page === null && size === null) {
      return res(
        ctx.status(200),
        ctx.json({
          studies: searchedStudies.slice(0, 5),
          hasNext: true,
        }),
      );
    }

    return res(
      ctx.status(200),
      ctx.json({
        studies: searchedStudies.slice(startIndex, endIndexExclusive),
        hasNext: endIndexExclusive < searchedStudies.length,
      }),
    );
  }),
  ...detailStudyHandlers,
  ...tagHandlers,
  ...myHandlers,
  ...reviewHandlers,
  ...tokenHandlers,
  ...memberHandlers,
  ...linkHandlers,
  ...communityHandlers,
  ...noticeHandlers,
];
