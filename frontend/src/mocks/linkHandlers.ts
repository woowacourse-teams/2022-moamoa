import { rest } from 'msw';

import linkJson from '@mocks/links.json';
import { user } from '@mocks/memberHandlers';

import type { PostLinkRequestBody, PutLinkRequestBody } from '@custom-types';

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

    return res(
      ctx.status(200),
      ctx.json({
        links: linkJson.links.slice(startIndex, endIndexExclusive),
        hasNext: endIndexExclusive < linkJson.links.length,
      }),
    );
  }),
  rest.post<PostLinkRequestBody>('/api/studies/:studyId/reference-room/links', (req, res, ctx) => {
    const { linkUrl, description } = req.body;
    if (!linkUrl || !description)
      return res(ctx.status(400), ctx.json({ message: 'linkeUrl 또는 description이 없음' }));

    linkJson.links = [
      {
        id: Math.random() * 100000 + 1000,
        author: user,
        linkUrl,
        description,
        createdDate: '2022-09-13',
        lastModifiedDate: '2022-09-13',
      },
      ...linkJson.links,
    ];

    return res(ctx.status(201));
  }),
  rest.put<PutLinkRequestBody>('/api/studies/:studyId/reference-room/links/:linkId', (req, res, ctx) => {
    const linkId = Number(req.params.linkId);
    if (!linkId) return res(ctx.status(400), ctx.json({ message: '링크 아이디가 없음' }));

    const { linkUrl, description } = req.body;
    if (!linkUrl || !description)
      return res(ctx.status(400), ctx.json({ message: 'linkeUrl 또는 description이 없음' }));

    const isExist = linkJson.links.some(link => link.id === linkId);
    if (!isExist) return res(ctx.status(404), ctx.json({ message: '해당하는 링크 없음' }));

    linkJson.links.forEach(link => {
      if (link.id === linkId) {
        link.linkUrl = linkUrl;
        link.description = description;
      }
    });
    return res(ctx.status(204));
  }),
  rest.delete('/api/studies/:studyId/reference-room/links/:linkId', (req, res, ctx) => {
    const linkId = Number(req.params.linkId);
    if (!linkId) return res(ctx.status(400), ctx.json({ message: '링크 아이디가 없음' }));

    const isExist = linkJson.links.some(link => link.id === linkId);
    if (!isExist) return res(ctx.status(404), ctx.json({ message: '해당하는 링크 없음' }));

    const filteredLinks = linkJson.links.filter(link => link.id !== linkId);
    linkJson.links = filteredLinks;
    return res(ctx.status(204));
  }),
];
