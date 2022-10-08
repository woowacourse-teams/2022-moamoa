import Flex from '@components/flex/Flex';

type CenterProps = {
  children?: React.ReactNode;
};

const Center: React.FC<CenterProps> = ({ children }) => (
  <Flex justifyContent="center" alignItems="center">
    {children}
  </Flex>
);

export default Center;
