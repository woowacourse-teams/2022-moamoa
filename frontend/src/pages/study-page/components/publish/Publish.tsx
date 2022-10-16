import { BoxButton } from '@shared/button';
import MetaBox from '@shared/meta-box/MetaBox';

type PublishProps = {
  title?: string;
  buttonText?: string;
};

const Publish = ({ title = '스터디 개설', buttonText = '개설하기' }: PublishProps) => {
  return (
    <MetaBox>
      <MetaBox.Title>{title}</MetaBox.Title>
      <MetaBox.Content>
        <PublishButton>{buttonText}</PublishButton>
      </MetaBox.Content>
    </MetaBox>
  );
};

export default Publish;

type PublishButtonProps = {
  children: string;
};
const PublishButton: React.FC<PublishButtonProps> = ({ children }) => (
  <BoxButton type="submit" fluid variant="secondary" custom={{ fontSize: 'lg' }}>
    {children}
  </BoxButton>
);
