import { rest } from 'msw';

import reviewJSON from '@mocks/reviews.json';

export const reviewHandlers = [
  rest.get('/api/studies/:studyId/reviews', (req, res, ctx) => {
    const size = req.url.searchParams.get('size');
    if (size) {
      const sizeNum = Number(size);
      return res(
        ctx.status(200),
        ctx.json({
          reviews: reviewJSON.reviews.slice(0, sizeNum),
          totalResults: reviewJSON.reviews.length,
        }),
      );
    }
    return res(
      ctx.status(200),
      ctx.json({
        reviews: reviewJSON.reviews,
        totalResults: reviewJSON.reviews.length,
      }),
    );
  }),

  rest.post('/api/studies/:studyId/reviews', (req, res, ctx) => {
    const studyId = req.params.studyId;
    console.log('studyId', studyId);
    console.log('req', req);
    const reviews = structuredClone(reviewJSON.reviews);
    reviews.push({
      id: 1,
      member: {
        id: 17899306,
        username: 'DsWB',
        imageUrl: 'https://picsum.photos/id/153/200/200',
        profileUrl: 'https://github.com/airman5573',
      },
      content:
        '우리는 봄바람을 목숨이 어디 같으며, 것은 뜨거운지라, 보배를 봄바람이다. 심장은 앞이 기쁘며, 설레는 것이다. 얼음에 갑 그것은 힘있다. 피가 소금이라 남는 길지 않는 그리하였는가? 피어나기 풀이 가슴이 뜨거운지라, 힘차게 무엇을 인생에 때문이다. 보내는 하였으며, 이상은 사랑의 얼마나 이상, 보이는 가는 별과 이것이다. 하는 주는 있으며, 하는 위하여 원대하고, 쓸쓸한 아름다우냐? 피고 내려온 위하여, 그들의 못하다 몸이 뭇 봄바람이다. 청춘의 끝까지 귀는 일월과 심장은 그러므로 어디 듣는다. 인간이 이상, 밥을 미인을 것이다. 원질이 소담스러운 이상의 이상, 얼마나 불어 싸인 뿐이다.',
      createdDate: '2022-07-12',
      lastModifiedDate: '',
    });
    return res(ctx.status(200), ctx.json({ reviews }));
  }),
];
