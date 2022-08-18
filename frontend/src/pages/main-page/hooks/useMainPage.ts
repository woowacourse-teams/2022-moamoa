import { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

import type { CategoryName, TagId, TagInfo } from '@custom-types';

import { useGetInfiniteStudies } from '@api/studies';

import { useAuth } from '@hooks/useAuth';

import { SearchContext } from '@context/search/SearchProvider';

const useMainPage = () => {
  const navigate = useNavigate();

  const { isLoggedIn } = useAuth();

  const { keyword } = useContext(SearchContext);
  const [selectedFilters, setSelectedFilters] = useState<Array<TagInfo>>([]);

  const studiesQueryResult = useGetInfiniteStudies({
    title: keyword,
    selectedFilters,
  });

  const handleFilterButtonClick = (id: TagId, categoryName: CategoryName) => () => {
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
    studiesQueryResult,
    selectedFilters,
    handleFilterButtonClick,
    handleCreateNewStudyButtonClick,
  };
};

export default useMainPage;
