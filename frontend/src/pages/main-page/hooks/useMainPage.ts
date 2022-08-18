import { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import type { TagInfo } from '@custom-types';

import { useAuth } from '@hooks/useAuth';

import { SearchContext } from '@context/search/SearchProvider';

import useGetInfiniteStudyList from '@main-page/hooks/useGetInfiniteStudyList';

const useMainPage = () => {
  const navigate = useNavigate();

  const { isLoggedIn } = useAuth();

  const { keyword } = useContext(SearchContext);
  const [selectedFilters, setSelectedFilters] = useState<Array<TagInfo>>([]);

  const studyListQueryResult = useGetInfiniteStudyList({
    title: keyword,
    selectedFilters,
  });

  const handleFilterButtonClick = (id: number, categoryName: string) => () => {
    setSelectedFilters(prev => {
      if (prev.some(filter => filter.id === id && filter.categoryName === categoryName)) {
        return prev.filter(filter => !(filter.id === id && filter.categoryName === categoryName));
      }
      return [...prev, { id, categoryName }];
    });
  };

  const handleCreateNewStudyButtonClick = () => {
    if (!isLoggedIn) {
      alert('로그인이 필요합니다');
      return;
    }
    window.scrollTo(0, 0);
    navigate(PATH.CREATE_STUDY);
  };

  return {
    studyListQueryResult,
    selectedFilters,
    handleFilterButtonClick,
    handleCreateNewStudyButtonClick,
  };
};

export default useMainPage;
