import { noop } from '@utils';

import type { MakeOptional } from '@custom-types';

import * as S from '@components/button/Button.style';

export type ButtonProps = {
  className?: string;
  children: string;
  type?: 'submit' | 'button';
  fluid?: boolean;
  disabled?: boolean;
  outline?: boolean;
  isLoading?: boolean;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};

type OptionalButtonProps = MakeOptional<ButtonProps, 'fluid' | 'onClick' | 'outline'>;

const LoadingIndicator: React.FC = () => {
  return (
    <S.LoadingIndicator>
      <div className="spinning-loader">
        <div className="dot"></div>
      </div>
    </S.LoadingIndicator>
  );
};

const Button: React.FC<OptionalButtonProps> = ({
  className,
  children,
  type = 'submit',
  fluid = true,
  disabled = false,
  outline = false,
  isLoading = false,
  onClick: handleClick = noop,
}) => {
  return (
    <S.ButtonContainer>
      <S.Button className={className} fluid={fluid} disabled={disabled} outline={outline} onClick={handleClick}>
        {/* isLoading상태에 관계 없이 children을 뿌려준다. 높이를 유지하기 위함이다.
        대신 color를 background-color와 동일하게 맞춘다 */}
        {children}
      </S.Button>
      {isLoading && <LoadingIndicator />}
    </S.ButtonContainer>
  );
};

export default Button;
