import { useContext, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { PATH } from '@constants';

import type { Member } from '@custom-types';

import { mqDown } from '@styles/responsive';

import { useAuth } from '@hooks/useAuth';
import { useUserInfo } from '@hooks/useUserInfo';

import { SearchContext } from '@context/search/SearchProvider';

import LogoLink from '@layout/header/components/logo-link/LogoLink';
import NavButton from '@layout/header/components/nav-button/NavButton';
import SearchBar from '@layout/header/components/search-bar/SearchBar';

import Avatar from '@shared/avatar/Avatar';
import { IconButton } from '@shared/button';
import DropDownBox from '@shared/drop-down-box/DropDownBox';
import Flex from '@shared/flex/Flex';
import { BookmarkIcon, FolderIcon, LoginIcon, LogoutIcon } from '@shared/icons';

import ButtonGroup from '@components/@shared/button-group/ButtonGroup';

const Header: React.FC = () => {
  const { setKeyword } = useContext(SearchContext);

  const [isOpenDropDownBox, setIsOpenDropDownBox] = useState(false);

  const navigate = useNavigate();
  const location = useLocation();

  const { logout, isLoggedIn } = useAuth();
  const { userInfo } = useUserInfo();

  const handleKeywordSubmit = (e: React.FormEvent<HTMLFormElement>, inputName: string) => {
    e.preventDefault();

    // TODO: 이건 어찌할 방법이 없을까?
    const value = (e.target as any)[inputName].value;
    if (value.length === 0) {
      alert('검색어를 입력해주세요');
      return;
    }
    setKeyword(value);
    navigate(PATH.MAIN);
  };

  const handleLoginButtonClick = () => {
    window.sessionStorage.setItem('prevPath', location.pathname);
  };

  const handleLogoutButtonClick = () => {
    logout();
  };

  const handleAvatarButtonClick = () => {
    setIsOpenDropDownBox(prev => !prev);
  };
  const handleDropDownBoxClose = () => setIsOpenDropDownBox(false);

  return (
    <Self>
      <LogoLink />
      <SearchBarContainer>
        <SearchBar onSubmit={handleKeywordSubmit} />
      </SearchBarContainer>
      {isLoggedIn ? (
        <nav>
          <Flex columnGap="16px">
            <MyStudyPageLink />
            <div
              css={css`
                position: relative;
              `}
            >
              <AvatarButton userInfo={userInfo} onClick={handleAvatarButtonClick} />
              <UserDropdown
                isOpen={isOpenDropDownBox}
                onClose={handleDropDownBoxClose}
                onLogoutButtonClick={handleLogoutButtonClick}
              />
            </div>
          </Flex>
        </nav>
      ) : (
        <LoginButton onClick={handleLoginButtonClick} />
      )}
    </Self>
  );
};

export default Header;

const Self = styled.header`
  ${({ theme }) => css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    column-gap: 20px;

    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 5;

    padding: 20px 40px;

    background-color: ${theme.colors.secondary.light};
    border-bottom: 1px solid ${theme.colors.secondary.dark};

    ${mqDown('md')} {
      padding: 16px 24px;
    }

    ${mqDown('sm')} {
      padding: 10px 12px;
    }
  `}
`;

const SearchBarContainer = styled.div`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  width: 100%;
  max-width: 400px;

  ${mqDown('lg')} {
    position: static;
    left: 0;
    top: 0;
    transform: none;
  }
`;

type LoginButtonProps = {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const LoginButton: React.FC<LoginButtonProps> = ({ onClick: handleClick }) => (
  <a href={`https://github.com/login/oauth/authorize?client_id=${process.env.CLIENT_ID}`}>
    <NavButton ariaLabel="로그인" onClick={handleClick}>
      <LoginIcon />
      <span>Github 로그인</span>
    </NavButton>
  </a>
);

const MyStudyPageLink = () => (
  <Link to={PATH.MY_STUDY}>
    <NavButton ariaLabel="내 스터디 페이지 이동">
      <BookmarkIcon />
      <span>내 스터디</span>
    </NavButton>
  </Link>
);

type UserDropdownProps = {
  isOpen: boolean;
  onClose: () => void;
  onLogoutButtonClick: () => void;
};
const UserDropdown: React.FC<UserDropdownProps> = ({
  isOpen,
  onClose: handleClose,
  onLogoutButtonClick: handleLogoutButtonClick,
}) => (
  <DropDownBox isOpen={isOpen} top="40px" right={0} onClose={handleClose} custom={{ padding: '16px' }}>
    <ButtonGroup orientation="vertical" gap="8px">
      <Link to={PATH.DRAFT}>
        <NavButton ariaLabel="임시 저장 목록 페이지 이동">
          <FolderIcon />
          <span>임시 저장 목록</span>
        </NavButton>
      </Link>
      <NavButton onClick={handleLogoutButtonClick} ariaLabel="로그아웃">
        <LogoutIcon />
        <span>로그아웃</span>
      </NavButton>
    </ButtonGroup>
  </DropDownBox>
);

type AvatarButtonProps = {
  userInfo: Member;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
};
const AvatarButton: React.FC<AvatarButtonProps> = ({ userInfo, onClick: handleClick }) => (
  <IconButton
    onClick={handleClick}
    ariaLabel={userInfo.username}
    variant="secondary"
    custom={{ width: '38px', height: '38px' }}
  >
    <Avatar src={userInfo.imageUrl} name={userInfo.username} />
  </IconButton>
);
