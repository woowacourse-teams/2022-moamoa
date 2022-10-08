import { rest } from 'msw';

import linkJson from '@mocks/links.json';
import { user } from '@mocks/memberHandlers';

import { type ApiLink } from '@api/link';

export const linkHandlers = [
  rest.get('/api/studies/:studyId/reference-room/links', (req, res, ctx) => {
    const page = req.url.searchParams.get('page');
    const size = req.url.searchParams.get('size');
    if ((size === null && page !== null) || (size !== null && page === null)) {
      return res(ctx.status(400), ctx.json({ message: 'size혹은 page가 없습니다' }));
    }

    const sizeNum = Number(size);
    const pageNum = Number(page);
    const startIndex = pageNum * sizeNum;
    const endIndexExclusive = startIndex + sizeNum;

    const { links } = linkJson;
    return res(
      ctx.status(200),
      ctx.json({
        links: links.slice(startIndex, endIndexExclusive),
        hasNext: endIndexExclusive < linkJson.links.length,
      }),
    );
  }),
  rest.post<ApiLink['post']['body']>('/api/studies/:studyId/reference-room/links', (req, res, ctx) => {
    const { linkUrl, description } = req.body;
    if (!linkUrl) return res(ctx.status(400), ctx.json({ message: 'linkeUrl이 없음' }));

    const { links } = linkJson;
    linkJson.links = [
      {
        id: Math.random() * 100000 + 1000,
        author: user,
        linkUrl,
        description,
        createdDate: '2022-09-13',
        lastModifiedDate: '2022-09-13',
      },
      ...links,
    ];

    return res(ctx.status(201));
  }),
  rest.put<ApiLink['put']['body']>('/api/studies/:studyId/reference-room/links/:linkId', (req, res, ctx) => {
    const linkId = Number(req.params.linkId);
    if (!linkId) return res(ctx.status(400), ctx.json({ message: '링크 아이디가 없음' }));

    const { linkUrl, description } = req.body;
    if (!linkUrl) return res(ctx.status(400), ctx.json({ message: 'linkeUrl이 없음' }));

    const { links } = linkJson;
    const isExist = links.some(link => link.id === linkId);
    if (!isExist) return res(ctx.status(404), ctx.json({ message: '해당하는 링크 없음' }));

    linkJson.links = links.map(link => {
      if (link.id === linkId)
        return {
          ...link,
          linkUrl,
          description,
        };
      return link;
    });
    return res(ctx.status(204));
  }),
  rest.delete('/api/studies/:studyId/reference-room/links/:linkId', (req, res, ctx) => {
    const linkId = Number(req.params.linkId);
    const { links } = linkJson;

    if (!linkId) return res(ctx.status(400), ctx.json({ message: '링크 아이디가 없음' }));

    const isExist = links.some(link => link.id === linkId);
    if (!isExist) return res(ctx.status(404), ctx.json({ message: '해당하는 링크 없음' }));

    const filteredLinks = links.filter(link => link.id !== linkId);
    linkJson.links = filteredLinks;
    return res(ctx.status(204));
  }),
];
