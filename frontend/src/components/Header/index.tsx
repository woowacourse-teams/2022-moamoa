import { SerializedStyles } from '@emotion/react';

import Avatar from '@components/Avatar';
import Logo from '@components/Logo';
import SearchBar from '@components/SearchBar';

import * as S from './style';

type Props = {
  className?: string;
  css?: SerializedStyles;
};

const Header: React.FC<Props> = ({ className }) => {
  return (
    <S.Row className={className}>
      <Logo />
      <S.SearchBarContainer>
        <SearchBar />
      </S.SearchBarContainer>
      <Avatar
        // TODO: Context에서 정보를 가져온다
        profileImg="https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80"
        profileAlt="프로필 이미지"
      />
    </S.Row>
  );
};

export default Header;
