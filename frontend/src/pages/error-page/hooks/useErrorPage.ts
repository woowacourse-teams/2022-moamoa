import { useNavigate } from 'react-router-dom';

import { PATH } from '@constants';

const useErrorPage = () => {
  const navigate = useNavigate();

  const handleHomeButtonClick = () => {
    navigate(PATH.MAIN, { replace: true });
  };

  return {
    handleHomeButtonClick,
  };
};

export default useErrorPage;
