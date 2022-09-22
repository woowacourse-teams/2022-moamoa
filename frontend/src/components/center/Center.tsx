import styled from '@emotion/styled';

type CenterProps = {
  children?: React.ReactNode;
};

const Center: React.FC<CenterProps> = ({ children }) => <Self>{children}</Self>;

const Self = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export default Center;
