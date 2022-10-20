import Flex from '@shared/flex/Flex';

type CenterProps = {
  children?: React.ReactNode;
};

const Center: React.FC<CenterProps> = ({ children }) => (
  <Flex justifyContent="center" alignItems="center">
    {children}
  </Flex>
);

export default Center;
