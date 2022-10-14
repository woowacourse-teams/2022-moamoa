import type { Member } from '@custom-types';

import UserInfoItem from '@shared/user-info-item/UserInfoItem';

export type UserDescriptionProps = {
  author: Member;
  description: string;
};

const UserDescription: React.FC<UserDescriptionProps> = ({ author, description }) => {
  return (
    <UserInfoItem src={author.imageUrl} name={author.username} size="sm">
      <UserInfoItem.Content>{description}</UserInfoItem.Content>
    </UserInfoItem>
  );
};

export default UserDescription;
