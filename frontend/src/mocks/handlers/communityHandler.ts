import { rest } from 'msw';

import communityArticlesJSON from '@mocks/community-articles.json';
import { user } from '@mocks/handlers/memberHandlers';

import { ApiCommunityArticle } from '@api/community';

export const communityHandlers = [
  rest.get('/api/studies/:studyId/community/articles', (req, res, ctx) => {
    const size = req.url.searchParams.get('size');
    const page = req.url.searchParams.get('page');

    const sizeNum = Number(size);
    const pageNum = Number(page);
    const startIndex = pageNum * sizeNum;
    const endIndexExclusive = startIndex + sizeNum;

    const { articles } = communityArticlesJSON;
    const totalCount = articles.length;

    const currentPage = page;
    const lastPage = Math.floor((totalCount - 1) / sizeNum);

    return res(
      ctx.status(200),
      ctx.json({
        articles: articles.slice(startIndex, endIndexExclusive),
        currentPage,
        lastPage,
        totalCount,
      }),
    );
  }),
  rest.get('/api/studies/:studyId/community/articles/:articleId', (req, res, ctx) => {
    const { studyId, articleId } = req.params;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));
    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: '게시글 아이디가 없음' }));

    const numArticleId = Number(articleId);

    const isExist = communityArticlesJSON.articles.some(article => article.id === Number(articleId));
    if (!isExist) return res(ctx.status(404), ctx.json({ message: '해당하는 게시글 없음' }));

    const article = communityArticlesJSON.articles.find(({ id }) => id === numArticleId);

    return res(ctx.status(200), ctx.json(article));
  }),
  rest.post<ApiCommunityArticle['post']['body']>('/api/studies/:studyId/community/articles', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

    const { title, content } = req.body;
    const newArticle = {
      id: Math.floor(Math.random() * 1000),
      author: user,
      title,
      content,
      createdDate: '2022-08-18',
      lastModifiedDate: '2022-08-18',
    };
    communityArticlesJSON.articles = [newArticle, ...communityArticlesJSON.articles];

    return res(ctx.status(201));
  }),
  rest.delete('/api/studies/:studyId/community/articles/:articleId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

    const articleId = req.params.articleId;
    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: 'article 아이디가 없음' }));

    const numArticleId = Number(articleId);

    communityArticlesJSON.articles = communityArticlesJSON.articles.filter(({ id }) => id !== numArticleId);

    return res(ctx.status(204));
  }),
  rest.put<ApiCommunityArticle['put']['body']>(
    '/api/studies/:studyId/community/articles/:articleId',
    (req, res, ctx) => {
      const studyId = req.params.studyId;
      if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

      const articleId = req.params.articleId;
      if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: 'article 아이디가 없음' }));

      const articles = communityArticlesJSON.articles;

      const isExist = articles.some(article => article.id === Number(articleId));
      if (!isExist) return res(ctx.status(404), ctx.json({ message: '해당하는 게시글 없음' }));

      const editedArticle = req.body;

      communityArticlesJSON.articles = articles.map(article => {
        if (article.id === Number(articleId)) {
          return { ...article, ...editedArticle };
        }
        return article;
      });

      return res(ctx.status(204));
    },
  ),
];
