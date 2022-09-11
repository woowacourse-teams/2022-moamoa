import Avatar, { AVATAR_SIZE } from '@design/components/avatar/Avatar';
import Flex from '@design/components/flex/Flex';
import * as S from '@design/components/item/Item.style';

export type ItemProps = {
  children: React.ReactNode;
  src: string;
  name: string;
  size: keyof typeof AVATAR_SIZE;
};

export type ItemHeadingProps = {
  children: React.ReactNode;
};

export type ItemContentProps = {
  children: React.ReactNode;
};

const Item: React.FC<ItemProps> = ({ children, src, name, size }) => {
  return (
    <S.Item>
      <Avatar src={src} name={name} size={size} />
      <Flex direction="column" rowGap="2px" grow>
        {children}
      </Flex>
    </S.Item>
  );
};

const ItemHeading: React.FC<ItemHeadingProps> = ({ children }) => {
  return <S.ItemHeading>{children}</S.ItemHeading>;
};

const ItemContent: React.FC<ItemContentProps> = ({ children }) => {
  return <S.ItemContent>{children}</S.ItemContent>;
};

export default Object.assign(Item, {
  Heading: ItemHeading,
  Content: ItemContent,
});
