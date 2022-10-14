import { Link } from 'react-router-dom';

import { PATH } from '@constants';

import NavButton from '@layout/header/components/nav-button/NavButton';

import { BookmarkIcon } from '@components/@shared/icons';

const MyStudyPageLink = () => (
  <Link to={PATH.MY_STUDY}>
    <NavButton ariaLabel="내 스터디">
      <BookmarkIcon />
      <span>내 스터디</span>
    </NavButton>
  </Link>
);

export default MyStudyPageLink;
