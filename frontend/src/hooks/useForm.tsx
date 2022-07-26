import { createContext, useContext, useRef, useState } from 'react';

import { isString } from '@utils/type-check';

// Field Types
type FieldName = string;
type FieldValues = Record<FieldName, any>;
type FieldErrors = Record<FieldName, FieldValidationResult>;

type FieldValidationResult = { hasError: boolean; errorMessage?: string };
type ValidateHandler = (val: any) => FieldValidationResult;
type ChangeHandler = (event: React.ChangeEvent<HTMLInputElement>) => void;

type Field = {
  _ref: HTMLInputElement;
  validate?: ValidateHandler;
  onChange?: ChangeHandler;
};

// Form
export type UseFormSubmitResult = {
  isValid: boolean;
  values?: FieldValues;
  errors?: FieldErrors;
};

type UseFormHandleSubmit = (
  onSubmit: (event: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => Promise<any> | undefined,
) => (event: React.FormEvent<HTMLFormElement>) => void;

type UseFormState = {
  isSubmitted: boolean;
  isSubmitting: boolean;
  isSubmitSuccessful: boolean;
  errors: FieldErrors;
  isValid: boolean;
};

type UseFormRegisterOption = Partial<{
  validate: ValidateHandler;
  onChange: ChangeHandler;
}>;

type RefCallBack = (instance: HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null) => void;

type UseFormRegisterReturn = {
  ref: RefCallBack;
  name: string;
};

type UseFormRegister = (name: string, options?: UseFormRegisterOption) => UseFormRegisterReturn;

type GetFieldFn = (name: string) => Field | null;

type UseFormReturn = {
  formState: UseFormState;
  handleSubmit: UseFormHandleSubmit;
  register: UseFormRegister;
  getField: GetFieldFn;
};

type UseForm = () => UseFormReturn;

export const useForm: UseForm = () => {
  const [formState, setFormState] = useState<UseFormState>({
    isSubmitted: false,
    isSubmitting: false,
    isSubmitSuccessful: false,
    errors: {},
    isValid: false,
  });

  const _fields = useRef<Map<string, Field>>();
  !_fields.current && (_fields.current = new Map<string, Field>());

  const getField: GetFieldFn = name => {
    if (!_fields.current) return null;
    const field = _fields.current.get(name);
    return field ?? null;
  };

  const getFieldValues = (fields: Map<string, Field>) => {
    const values = [...fields.keys()].reduce((acc, name) => {
      const field = getField(name);
      if (!field) return acc;
      const { _ref } = field;

      acc[name] = _ref.type === 'checkbox' ? (_ref.checked ? 'checked' : '') : _ref.value;
      return acc;
    }, {} as Record<string, string>);
    return values;
  };

  const getFieldValue = ({ _ref }: Field) => {
    return _ref.type === 'checkbox' ? (_ref.checked ? 'checked' : '') : _ref.value;
  };

  const getFieldErrors = (fields: Map<string, Field>): Record<string, FieldValidationResult> => {
    // validation
    const errors: FieldErrors = [...fields.keys()].reduce((acc, name) => {
      const field = getField(name);
      if (!field) return acc;

      const { validate } = field;
      if (!validate) return acc;

      const value = getFieldValue(field);
      const validationResult = validate(value);
      acc[name] = validationResult;

      return acc;
    }, {} as Record<string, FieldValidationResult>);

    return errors;
  };

  const handleSubmit: UseFormHandleSubmit = onSubmit => (event: any) => {
    event.preventDefault();
    if (!_fields.current) return;

    // 초기화를 한다
    setFormState(() => ({
      errors: {},
      isSubmitting: true,
      isSubmitted: false,
      isSubmitSuccessful: false,
      isValid: true,
    }));

    const values = getFieldValues(_fields.current);
    const errors = getFieldErrors(_fields.current);
    const isValid = Object.keys(errors).length > 0;

    if (!isValid) {
      setFormState(() => ({
        errors,
        isSubmitting: false,
        isSubmitted: false,
        isSubmitSuccessful: false,
        isValid: false,
      }));
      return;
    }

    const result = onSubmit(event, { isValid, values, errors });
    if (result) {
      result
        .then(() => {
          setFormState(() => ({
            errors: {},
            isSubmitting: false,
            isSubmitted: true,
            isSubmitSuccessful: true,
            isValid,
          }));
        })
        .catch(() => {
          setFormState(() => ({
            errors: {}, // TODO: 이래도 되나 싶다
            isSubmitting: false,
            isSubmitted: true,
            isSubmitSuccessful: false,
            isValid: false,
          }));
        })
        .finally(() => {
          setFormState(() => ({
            errors: {},
            isSubmitting: false,
            isSubmitted: false,
            isSubmitSuccessful: false,
            isValid: false,
          }));
        });
    }
  };

  const register: UseFormRegister = (name, options) => {
    return {
      ref: (ref: any) => {
        if (!ref) return;
        if (!_fields.current) return;

        _fields.current.set(name, {
          _ref: ref,
          validate: options?.validate,
        });
      },
      name,
    };
  };

  return {
    formState,
    handleSubmit,
    register,
    getField,
  };
};

// Context
export type FormProviderProps = {
  children: React.ReactNode;
} & UseFormReturn;

const FormContext = createContext<UseFormReturn | null>(null);
FormContext.displayName = 'HookFormContext';

export const useFormContext = (): UseFormReturn => useContext(FormContext) as unknown as UseFormReturn;

export const FormProvider = (props: FormProviderProps) => {
  const { children, ...data } = props;

  return <FormContext.Provider value={data}>{children}</FormContext.Provider>;
};
