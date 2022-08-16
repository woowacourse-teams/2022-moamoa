import { rest } from 'msw';

export const user = {
  id: 20,
  username: 'tco0427',
  imageUrl:
    'https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80',
  profileUrl: 'github.com',
};

export const memberHandlers = [
  rest.get('/api/members/me', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(user));
  }),
  rest.get('/api/members/me/role', (req, res, ctx) => {
    // const studyId = req.url.searchParams.get('study-id');

    // const roles = ['OWNER', 'MEMBER', 'NON_MEMBER'];
    // const selectedRole = roles[Math.floor(Math.random() * roles.length)];
    return res(
      ctx.status(200),
      ctx.json({
        role: 'OWNER',
        // role: selectedRole,
      }),
    );
  }),
];
