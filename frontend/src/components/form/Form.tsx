import { noop } from '@utils';

export type FormProps = {
  children: React.ReactNode;
  onSubmit?: React.FormEventHandler<HTMLFormElement>;
};

const Form: React.FC<FormProps> = ({ children, onSubmit: handleSubmit = noop }) => {
  return <form onSubmit={handleSubmit}>{children}</form>;
};

export default Form;
