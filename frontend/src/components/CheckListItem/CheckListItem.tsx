import { FiCheck } from 'react-icons/fi';

import * as S from './CheckListItem.style';

export interface CheckListItemProps {
  children: string;
  isChecked?: boolean;
}

const CheckListItem: React.FC<CheckListItemProps> = ({ children, isChecked = false }) => {
  return (
    <S.ItemContainer isChecked={isChecked}>
      <FiCheck />
      <span>{children}</span>
    </S.ItemContainer>
  );
};

export default CheckListItem;
