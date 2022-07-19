import { setupWorker } from 'msw';

import { filterHandlers } from './filterHandlers';
import { handlers } from './handlers';

export const worker = setupWorker(...handlers, ...filterHandlers);
