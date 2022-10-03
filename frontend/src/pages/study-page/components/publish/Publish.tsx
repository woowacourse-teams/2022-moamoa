import { Theme, useTheme } from '@emotion/react';

import { BoxButton } from '@components/button';
import MetaBox from '@components/meta-box/MetaBox';

type PublishProps = {
  title?: string;
  buttonText?: string;
};

const Publish = ({ title = '스터디 개설', buttonText = '개설하기' }: PublishProps) => {
  const theme = useTheme();

  return (
    <MetaBox>
      <MetaBox.Title>{title}</MetaBox.Title>
      <MetaBox.Content>
        <PublishButton theme={theme}>{buttonText}</PublishButton>
      </MetaBox.Content>
    </MetaBox>
  );
};

type PublishButtonProps = {
  theme: Theme;
  children: string;
};
const PublishButton: React.FC<PublishButtonProps> = ({ theme, children }) => (
  <BoxButton type="submit" fluid variant="secondary" custom={{ fontSize: theme.fontSize.lg }}>
    {children}
  </BoxButton>
);

export default Publish;
