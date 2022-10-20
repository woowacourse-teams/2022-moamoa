import { rest } from 'msw';

import { user } from '@mocks/handlers/memberHandlers';
import noticeArticlesJSON from '@mocks/notice-articles.json';
import noticeCommentJSON from '@mocks/notice-comment.json';

import { type ApiNoticeArticle } from '@api/notice';
import { type ApiNoticeComment } from '@api/notice-comment';

export const noticeHandlers = [
  rest.get('/api/studies/:studyId/notice/articles', (req, res, ctx) => {
    const size = req.url.searchParams.get('size');
    const page = req.url.searchParams.get('page');

    const sizeNum = Number(size);
    const pageNum = Number(page);
    const startIndex = pageNum * sizeNum;
    const endIndexExclusive = startIndex + sizeNum;

    const { articles } = noticeArticlesJSON;
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
  rest.get('/api/studies/:studyId/notice/articles/:articleId', (req, res, ctx) => {
    const { studyId, articleId } = req.params;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: '게시글 아이디가 없음' }));

    const numArticleId = Number(articleId);

    const article = noticeArticlesJSON.articles.filter(({ id }) => id === numArticleId);

    return res(ctx.status(200), ctx.json(article[0]));
  }),
  rest.post<ApiNoticeArticle['post']['body']>('/api/studies/:studyId/notice/articles', (req, res, ctx) => {
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
    noticeArticlesJSON.articles = [newArticle, ...noticeArticlesJSON.articles];

    return res(ctx.status(201));
  }),
  rest.delete('/api/studies/:studyId/notice/articles/:articleId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

    const articleId = req.params.articleId;
    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: 'article 아이디가 없음' }));

    const numArticleId = Number(articleId);

    noticeArticlesJSON.articles = noticeArticlesJSON.articles.filter(({ id }) => id !== numArticleId);

    return res(ctx.status(201));
  }),
  rest.put<ApiNoticeArticle['put']['body']>('/api/studies/:studyId/notice/articles/:articleId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

    const articleId = req.params.articleId;
    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: 'article 아이디가 없음' }));

    const articles = noticeArticlesJSON.articles;

    const isExist = articles.some(article => article.id === Number(articleId));
    if (!isExist) return res(ctx.status(404), ctx.json({ message: '해당하는 게시글 없음' }));

    const editedArticle = req.body;

    noticeArticlesJSON.articles = articles.map(article => {
      if (article.id === Number(articleId)) {
        return { ...article, ...editedArticle };
      }
      return article;
    });

    return res(ctx.status(204));
  }),

  // comments
  rest.get('/api/studies/:studyId/notice/:articleId/comments', (req, res, ctx) => {
    const _size = req.url.searchParams.get('size');
    const _page = req.url.searchParams.get('page');

    if (_size && _page) {
      const size = Number(_size);
      const page = _page ? Number(_page) : 0;
      const startIndex = page * size;
      const endIndexExclusive = startIndex + size;

      return res(
        ctx.status(200),
        ctx.json({
          comments: noticeCommentJSON.comments.slice(startIndex, endIndexExclusive),
          totalCount: noticeCommentJSON.comments.length,
          hasNext: noticeCommentJSON.comments.length > size * page,
        }),
      );
    }

    if (_size) {
      const size = Number(_size);
      return res(
        ctx.status(200),
        ctx.json({
          comments: noticeCommentJSON.comments.slice(0, size),
          totalCount: noticeCommentJSON.comments.length,
        }),
      );
    }
    return res(
      ctx.status(200),
      ctx.json({
        comments: noticeCommentJSON.comments,
        totalCount: noticeCommentJSON.comments.length,
      }),
    );
  }),
  rest.post<ApiNoticeComment['post']['body']>('/api/studies/:studyId/notice/:articleId/comments', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

    const articleId = req.params.articleId;
    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: '게시글 아이디가 없음' }));

    const content = req.body['content'];
    if (!content) return res(ctx.status(400), ctx.json({ errorMessage: '댓글내용이 없습니다' }));

    const comment = {
      id: 1,
      author: user,
      content,
      createdDate: '2022-07-12',
      lastModifiedDate: '2022-07-13',
    };
    const comments = [comment, ...noticeCommentJSON.comments];
    noticeCommentJSON.comments = comments;

    return res(ctx.status(200));
  }),
  rest.delete('/api/studies/:studyId/notice/:articleId/comments/:commentId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

    const articleId = req.params.articleId;
    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: '게시글 아이디가 없음' }));

    const _commentId = req.params.commentId;
    if (!_commentId) return res(ctx.status(400), ctx.json({ errorMessage: '댓글 아이디 아이디가 없음' }));

    const commentId = Number(_commentId);

    noticeCommentJSON.comments = noticeCommentJSON.comments.filter(({ id }) => commentId !== id);

    return res(ctx.status(200));
  }),
  rest.put<ApiNoticeComment['put']['body']>(
    '/api/studies/:studyId/notice/:articleId/comments/:commentId',
    (req, res, ctx) => {
      const studyId = req.params.studyId;
      if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

      const articleId = req.params.articleId;
      if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: '게시글 아이디가 없음' }));

      const _commentId = req.params.commentId;
      if (!_commentId) return res(ctx.status(400), ctx.json({ errorMessage: '댓글 아이디 아이디가 없음' }));

      const content = req.body['content'];
      if (!content) return res(ctx.status(400), ctx.json({ errorMessage: '댓글내용이 없습니다' }));

      const commentId = Number(_commentId);

      noticeCommentJSON.comments = noticeCommentJSON.comments.map(comment => {
        if (comment.id === commentId) {
          comment.content = content;
        }
        return comment;
      });

      return res(ctx.status(200));
    },
  ),
];
