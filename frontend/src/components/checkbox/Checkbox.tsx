import * as S from './Checkbox.style';

export type CheckboxProps = React.HTMLProps<HTMLInputElement>;

const Checkbox: React.FC<CheckboxProps> = ({ className, id, checked, onChange }) => {
  return <S.Checkbox type="checkbox" id={id} className={className} checked={checked} onChange={onChange} />;
};

export default Checkbox;
