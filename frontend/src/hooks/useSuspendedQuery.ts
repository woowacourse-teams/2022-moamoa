// from: https://github.com/toss/slash/blob/main/packages/react/react-query/src/hooks/useSuspendedQuery.ts
// useSuspendedQuery가 필요한이유 (https://github.com/TanStack/query/issues/1297)
// suspense option을 사용했을때 예상과는 달리 useQuery의 return data의 type이 undefined입니다.
// 이 이유는 suspense를 사용한다는것이 data가 undefined가 아니라고 단정짓기 어려운 상황이 있기 때문입니다.
// 예를들어 suspense는 켰으나 enabled가 false인 경우 실제적으로 query가 실행되지 않았기 때문에 suspense는 작동하지 않고
// data도 undefined입니다.
// 또한 queryClient.cancelQueries를 호출해서 query를 취소할때도 data는 undefined입니다.
// 다시말해서 suspense와 data는 상관이 없기 때문에 이렇게 추가적으로 훅을 만들어준것입니다.
// 물론 이 훅도 완벽하지 않습니다, typescript를 바탕으로 enabled를 못넣게 하거나 true만 넣을 수 있도록 했는데
// 위에서 언급한대로 enabled말고도 cancelQueries도 있기 때문입니다.
// 하지만 일반적으로 query를 cancel할 일은 없기 때문에 이 훅을 사용해도 좋습니다.
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
