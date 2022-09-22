import Avatar, { AVATAR_SIZE } from '@components/avatar/Avatar';
import Flex from '@components/flex/Flex';
import * as S from '@components/user-info-item/UserInfoItem.style';

export type UserInfoItemProps = {
  children: React.ReactNode;
  src: string;
  name: string;
  size: keyof typeof AVATAR_SIZE;
};

export type UserInfoItemHeadingProps = {
  children: React.ReactNode;
};

export type UserInfoItemContentProps = {
  children: React.ReactNode;
};

const UserInfoItem: React.FC<UserInfoItemProps> = ({ children, src, name, size }) => {
  return (
    <S.Item>
      <Avatar src={src} name={name} size={size} />
      <Flex flexDirection="column" columnGap="2px">
        {children}
      </Flex>
    </S.Item>
  );
};

const UserInfoItemHeading: React.FC<UserInfoItemHeadingProps> = ({ children }) => {
  return <S.ItemHeading>{children}</S.ItemHeading>;
};

const UserInfoItemContent: React.FC<UserInfoItemContentProps> = ({ children }) => {
  return <S.ItemContent>{children}</S.ItemContent>;
};

export default Object.assign(UserInfoItem, {
  Heading: UserInfoItemHeading,
  Content: UserInfoItemContent,
});
