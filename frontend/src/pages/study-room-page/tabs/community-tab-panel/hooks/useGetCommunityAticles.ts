import { useQuery } from 'react-query';

import getCommunityArticles from '@api/getCommunityArticles';

const useGetCommunityArticles = (studyId: number, page: number) => {
  return useQuery(['get-community-articles', studyId, page], () => getCommunityArticles({ studyId, page }), {});
};

export default useGetCommunityArticles;
