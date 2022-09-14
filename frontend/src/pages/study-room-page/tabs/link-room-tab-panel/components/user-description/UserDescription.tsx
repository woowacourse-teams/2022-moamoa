import type { Member } from '@custom-types';

import Item from '@components/item/Item';

export type UserDescriptionProps = {
  author: Member;
  description: string;
};

const UserDescription: React.FC<UserDescriptionProps> = ({ author, description }) => {
  return (
    <Item src={author.imageUrl} name={author.username} size="sm">
      <Item.Content>{description}</Item.Content>
    </Item>
  );
};

export default UserDescription;
