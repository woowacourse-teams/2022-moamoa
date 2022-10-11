import { setupWorker } from 'msw';

import { handlers } from '@mocks/handlers/handlers';

export const worker = setupWorker(...handlers);
