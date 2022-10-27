import { rest } from 'msw';

import communityArticlesJSON from '@mocks/community-articles.json';
import communityCommentJSON from '@mocks/community-comment.json';
import communityDraftArticlesJSON from '@mocks/community-draft-articles.json';
import { user } from '@mocks/handlers/memberHandlers';

import { type ApiCommunityArticle } from '@api/community/article';
import { type ApiCommunityComment } from '@api/community/comment';
import { type ApiCommunityDraftArticle, type ApiCommunityDraftArticleToArticle } from '@api/community/draft-article';

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

    const currentPage = pageNum;
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

  // Comments
  rest.get('/api/studies/:studyId/community/:articleId/comments', (req, res, ctx) => {
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
          comments: communityCommentJSON.comments.slice(startIndex, endIndexExclusive),
          totalCount: communityCommentJSON.comments.length,
          hasNext: communityCommentJSON.comments.length > size * page,
        }),
      );
    }

    if (_size) {
      const size = Number(_size);
      return res(
        ctx.status(200),
        ctx.json({
          comments: communityCommentJSON.comments.slice(0, size),
          totalCount: communityCommentJSON.comments.length,
        }),
      );
    }
    return res(
      ctx.status(200),
      ctx.json({
        comments: communityCommentJSON.comments,
        totalCount: communityCommentJSON.comments.length,
      }),
    );
  }),
  rest.post<ApiCommunityComment['post']['body']>(
    '/api/studies/:studyId/community/:articleId/comments',
    (req, res, ctx) => {
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
      const comments = [comment, ...communityCommentJSON.comments];
      communityCommentJSON.comments = comments;

      return res(ctx.status(200));
    },
  ),
  rest.delete('/api/studies/:studyId/community/:articleId/comments/:commentId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

    const articleId = req.params.articleId;
    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: '게시글 아이디가 없음' }));

    const _commentId = req.params.commentId;
    if (!_commentId) return res(ctx.status(400), ctx.json({ errorMessage: '댓글 아이디 아이디가 없음' }));

    const commentId = Number(_commentId);

    communityCommentJSON.comments = communityCommentJSON.comments.filter(({ id }) => commentId !== id);

    return res(ctx.status(200));
  }),
  rest.put<ApiCommunityComment['put']['body']>(
    '/api/studies/:studyId/community/:articleId/comments/:commentId',
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

      communityCommentJSON.comments = communityCommentJSON.comments.map(comment => {
        if (comment.id === commentId) {
          comment.content = content;
        }
        return comment;
      });

      return res(ctx.status(200));
    },
  ),

  // community-draft
  rest.get('/api/draft/community/articles', (req, res, ctx) => {
    const size = req.url.searchParams.get('size');
    const page = req.url.searchParams.get('page');

    const sizeNum = Number(size);
    const pageNum = Number(page);
    const startIndex = pageNum * sizeNum;
    const endIndexExclusive = startIndex + sizeNum;

    const { articles } = communityDraftArticlesJSON;
    const totalCount = articles.length;

    const currentPage = pageNum;
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
  rest.get('/api/studies/:studyId/community/draft-articles/:articleId', (req, res, ctx) => {
    const { studyId, articleId } = req.params;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));
    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: '게시글 아이디가 없음' }));

    const numArticleId = Number(articleId);

    const isExist = communityDraftArticlesJSON.articles.some(article => article.id === Number(articleId));
    if (!isExist) return res(ctx.status(404), ctx.json({ message: '해당하는 게시글 없음' }));

    const article = communityDraftArticlesJSON.articles.find(({ id }) => id === numArticleId);

    return res(ctx.status(200), ctx.json(article));
  }),
  rest.post<ApiCommunityDraftArticle['post']['body']>(
    '/api/studies/:studyId/community/draft-articles',
    (req, res, ctx) => {
      const studyId = req.params.studyId;
      if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

      const { title, content } = req.body;
      const newArticle = {
        id: Math.floor(Math.random() * 1000),
        title,
        content,
        createdDate: '2022-08-18',
        lastModifiedDate: '2022-08-18',
        study: {
          id: 79384333,
          title: '2022-daily-planner',
        },
      };
      communityDraftArticlesJSON.articles = [newArticle, ...communityDraftArticlesJSON.articles];

      return res(ctx.status(201), ctx.json({ draftArticleId: newArticle.id }));
    },
  ),
  rest.post<ApiCommunityDraftArticleToArticle['post']['body']>(
    '/api/studies/:studyId/community/draft-articles/:articleId/publish',
    (req, res, ctx) => {
      const { studyId, articleId } = req.params;
      if (!studyId || !articleId) return res(ctx.status(400), ctx.json({ errorMessage: '아이디가 없음' }));

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
    },
  ),
  rest.delete('/api/studies/:studyId/community/draft-articles/:articleId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

    const articleId = req.params.articleId;
    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: 'article 아이디가 없음' }));

    const numArticleId = Number(articleId);

    communityDraftArticlesJSON.articles = communityDraftArticlesJSON.articles.filter(({ id }) => id !== numArticleId);

    return res(ctx.status(204));
  }),
  rest.put<ApiCommunityDraftArticle['put']['body']>(
    '/api/studies/:studyId/community/draft-articles/:articleId',
    (req, res, ctx) => {
      const studyId = req.params.studyId;
      if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가 없음' }));

      const articleId = req.params.articleId;
      if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: 'article 아이디가 없음' }));

      const articles = communityDraftArticlesJSON.articles;

      const isExist = articles.some(article => article.id === Number(articleId));
      if (!isExist) return res(ctx.status(404), ctx.json({ message: '해당하는 게시글 없음' }));

      const editedArticle = req.body;

      communityDraftArticlesJSON.articles = articles.map(article => {
        if (article.id === Number(articleId)) {
          return { ...article, ...editedArticle };
        }
        return article;
      });

      return res(ctx.status(204));
    },
  ),
];
