import type { Member } from '@custom-types';

import Avatar from '@components/avatar/Avatar';

import * as S from '@study-room-page/tabs/link-room-tab-panel/components/user-description/UserDescription.style';

export type UserDescriptionProps = {
  className?: string;
  author: Member;
  description: string;
};

const UserDescription: React.FC<UserDescriptionProps> = ({ author, description, className }) => {
  return (
    <S.Container className={className}>
      <Avatar size="xs" profileImg={author.imageUrl} profileAlt={`${author.username} 프로필 이미지`} />
      <S.Description>{description}</S.Description>
    </S.Container>
  );
};

export default UserDescription;
