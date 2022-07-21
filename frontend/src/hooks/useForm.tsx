import { createContext, useContext, useRef, useState } from 'react';

import { isString } from '@utils/type-check';

// Field Types
type FieldName = string;
type FieldValues = Record<FieldName, any>;
type FieldErrors = Record<FieldName, ErrorMessage>;
type ErrorMessage = string;

type ValidateHandler = (val: any) => string | boolean;
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
  onSubmit: (event: React.FormEvent<HTMLFormElement>, submitResult: UseFormSubmitResult) => Promise<any>,
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

  const validateField = (name: string): ErrorMessage | null => {
    const field = getField(name);
    if (!field) return null;

    const { _ref, validate } = field;
    const input = _ref as HTMLInputElement;
    const { value } = input;

    if (validate) {
      const result = validate(value);
      let errorMessage: null | string = null;
      if (result === false) {
        errorMessage = '다시 확인해주세요';
      } else if (isString(result)) {
        errorMessage = result;
      }
      return errorMessage;
    }

    // 문제가 없는 경우
    return null;
  };

  const handleSubmit: UseFormHandleSubmit = onSubmit => (event: any) => {
    event.preventDefault();
    if (!_fields.current) return;

    // 초기화를 한다
    setFormState(prev => ({
      errors: {},
      isSubmitting: true,
      isSubmitted: false,
      isSubmitSuccessful: false,
      isValid: false,
    }));

    const values = [..._fields.current.keys()].reduce((acc, name) => {
      const field = getField(name);
      if (!field) return acc;

      acc[name] = field._ref.value;
      return acc;
    }, {} as Record<string, string>);

    // validation
    const errors: FieldErrors = {};
    let isValid = true;
    Object.keys(values).forEach(name => {
      const errorMessage = validateField(name);
      if (errorMessage) {
        isValid = false;
        errors[name] = errorMessage;
      }
    });

    onSubmit(event, { isValid, values, errors })
      .then(() => {
        setFormState(prev => ({
          errors: {},
          isSubmitting: false,
          isSubmitted: true,
          isSubmitSuccessful: true,
          isValid,
        }));
      })
      .catch((e: any) => {
        setFormState(prev => ({
          errors: {}, // TODO: 이래도 되나 싶다
          isSubmitting: false,
          isSubmitted: true,
          isSubmitSuccessful: false,
          isValid: false,
        }));
      });
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
