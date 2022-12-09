// from: https://github.com/toss/slash/blob/main/packages/react/react-query/src/hooks/useSuspendedQuery.ts
import { type QueryFunction, type QueryKey, type UseQueryOptions, type UseQueryResult, useQuery } from 'react-query';

import { Merge } from '@custom-types';

export type BaseUseSuspendedQueryResult<TData> = Merge<
  Omit<UseQueryResult<TData, never>, 'data' | 'status' | 'error' | 'isLoading' | 'isError' | 'isFetching'>,
  {
    data: TData;
    status: 'success' | 'idle';
  }
>;

export type UseSuspendedQueryResultOnSuccess<TData> = BaseUseSuspendedQueryResult<TData> & {
  status: 'success';
  isSuccess: true;
  isIdle: false;
};
export type UseSuspendedQueryResultOnIdle<TData> = BaseUseSuspendedQueryResult<TData> & {
  status: 'idle';
  isSuccess: false;
  isIdle: true;
};

export type UseSuspendedQueryOption<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
> = Omit<UseQueryOptions<TQueryFnData, TError, TData, TQueryKey>, 'suspense'>;

export function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options?: Omit<UseSuspendedQueryOption<TQueryFnData, TError, TData, TQueryKey>, 'enabled'>,
): UseSuspendedQueryResultOnSuccess<TData>;
export function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options: Omit<UseSuspendedQueryOption<TQueryFnData, TError, TData, TQueryKey>, 'enabled'> & {
    enabled?: true;
  },
): UseSuspendedQueryResultOnSuccess<TData>;
export function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options: Omit<UseSuspendedQueryOption<TQueryFnData, TError, TData, TQueryKey>, 'enabled'> & {
    enabled: false;
  },
): UseSuspendedQueryResultOnIdle<undefined>;
export function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options: UseSuspendedQueryOption<TQueryFnData, TError, TData, TQueryKey>,
): UseSuspendedQueryResultOnSuccess<TData> | UseSuspendedQueryResultOnIdle<undefined>;
export function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  queryKey: TQueryKey,
  queryFn: QueryFunction<TQueryFnData, TQueryKey>,
  options?: UseSuspendedQueryOption<TQueryFnData, TError, TData, TQueryKey>,
) {
  return useQuery(queryKey, queryFn, { ...options, suspense: true }) as BaseUseSuspendedQueryResult<TData>;
}
