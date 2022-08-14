import { rest } from 'msw';

import linkJson from '@mocks/links.json';

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

    const selectedLinks = linkJson.links.slice(startIndex, endIndexExclusive);

    return res(
      ctx.status(200),
      ctx.json({
        links: selectedLinks,
        hasNext: endIndexExclusive < selectedLinks.length,
      }),
    );
  }),
  rest.post<PostLinkRequestBody>('/api/studies/:studyId/reference-room/links', (req, res, ctx) => {
    const { linkUrl, description } = req.body;
    if (!linkUrl || !description)
      return res(ctx.status(400), ctx.json({ message: 'linkeUrl 또는 description이 없음' }));

    linkJson.links = [
      {
        id: 7,
        author: {
          username: 'person',
          imageUrl:
            'https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60',
          profileUrl: 'github.com/jjanggu',
          id: 1,
        },
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
