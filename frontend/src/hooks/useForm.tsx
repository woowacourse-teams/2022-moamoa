import { createContext, useContext, useRef, useState } from 'react';

import { MakeRequired } from '@custom-types/index';

type FieldName = string;
type FieldValues = Record<FieldName, any>;
type FieldErrors = Record<FieldName, FieldValidationResult>;

type FieldValidationResult = { hasError: boolean; errorMessage?: string };
type ValidateHandler = (val: any) => FieldValidationResult;
type ChangeHandler = React.ChangeEventHandler<FieldElement>;
type FieldElement = HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement;
type ValidationMode = 'change' | 'submit';

type Field = {
  fieldElement: FieldElement;
} & UseFormRegisterOption;

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
  validationMode: ValidationMode;
  onChange: ChangeHandler;
  minLength: number;
  maxLength: number;
  min: number;
  max: number;
}>;

type RefCallBack = (element: FieldElement | null) => void;

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

export const makeValidationResult = (hasError: boolean, errorMessage?: string) => ({
  hasError,
  errorMessage,
});

const initialFormState = {
  errors: {},
  isSubmitting: false,
  isSubmitted: false,
  isSubmitSuccessful: false,
  isValid: true,
};

export const useForm: UseForm = () => {
  const [formState, setFormState] = useState<UseFormState>({ ...initialFormState });

  const fieldsRef = useRef<Map<string, Field>>();
  if (!fieldsRef.current) {
    fieldsRef.current = new Map<string, Field>();
  }

  const getField: GetFieldFn = name => {
    if (!fieldsRef.current) return null;
    const field = fieldsRef.current.get(name);
    return field ?? null;
  };

  const getFieldValue = ({ fieldElement }: Field) => {
    if (fieldElement instanceof HTMLInputElement) {
      if (fieldElement.type === 'checkbox') {
        return fieldElement.checked ? 'checked' : 'unchecked';
      }
    }
    return fieldElement.value;
  };

  const getFieldValues = (fields: Map<string, Field>) => {
    const values = [...fields.keys()].reduce((acc, name) => {
      const field = getField(name);
      if (!field) return acc;

      acc[name] = getFieldValue(field);
      return acc;
    }, {} as Record<string, string>);
    return values;
  };

  const getFieldErrors = (fields: Map<string, Field>): Record<string, FieldValidationResult> => {
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

  const removeError = (prev: UseFormState, name: FieldName): UseFormState => {
    const errors = { ...prev.errors };
    delete errors[name];
    return {
      ...prev,
      errors,
      isValid: Object.keys(errors).length === 0,
    };
  };

  const setError = (prev: UseFormState, error: Record<FieldName, FieldValidationResult>): UseFormState => {
    const errors = { ...prev.errors, ...error };
    return {
      ...prev,
      errors,
      isValid: Object.keys(errors).length === 0,
    };
  };

  const validateFieldOnChange = (field: Field) => {
    const {
      validate,
      fieldElement: { name, value },
      min,
      max,
      minLength,
      maxLength,
    } = field;

    // Custom Validation
    if (!validate) return;
    const validationResult = validate(value);
    const error = { [name]: validationResult };
    if (!validationResult.hasError) {
      setFormState(prev => removeError(prev, name));
      return;
    }

    setFormState(prev => setError(prev, error));
  };

  const handleChange = (e: React.ChangeEvent<FieldElement>) => {
    const {
      target: { name },
    } = e;
    const field = getField(name);
    if (!field) return;

    if (field.onChange) field.onChange(e);

    validateFieldOnChange(field);
  };

  const handleSubmit: UseFormHandleSubmit = onSubmit => (event: any) => {
    event.preventDefault();
    if (!fieldsRef.current) return;

    setFormState(() => ({
      errors: {},
      isSubmitting: true,
      isSubmitted: false,
      isSubmitSuccessful: false,
      isValid: true,
    }));

    const errors = getFieldErrors(fieldsRef.current);
    const isValid = Object.keys(errors).length === 0;

    if (!isValid) {
      setFormState({
        ...initialFormState,
        errors,
        isValid: false,
      });
      return;
    }

    const values = getFieldValues(fieldsRef.current);
    const result = onSubmit(event, { isValid, values, errors });
    if (result) {
      result
        .then(() => {
          setFormState({
            ...initialFormState,
            isSubmitted: true,
            isSubmitSuccessful: true,
          });
        })
        .catch(() => {
          setFormState({
            ...initialFormState,
            isSubmitted: true,
            isValid: false,
          });
        });
    }
  };

  const register: UseFormRegister = (name, options) => {
    return {
      ref: (element: FieldElement | null) => {
        if (!element) return;
        if (!fieldsRef.current) return;

        fieldsRef.current.set(name, {
          fieldElement: element,
          onChange: options?.onChange,
          validate: options?.validate,
          validationMode: options?.validationMode,
        });
      },
      name,
      onChange: handleChange,
      maxLength: options?.maxLength,
      minLength: options?.minLength,
      max: options?.max,
      min: options?.min,
    };
  };

  return {
    formState,
    handleSubmit,
    register,
    getField,
  };
};

export type FormProviderProps = {
  children: React.ReactNode;
} & UseFormReturn;

const FormContext = createContext<UseFormReturn | null>(null);
FormContext.displayName = 'HookFormContext';

export const useFormContext = (): UseFormReturn => {
  return useContext(FormContext) as UseFormReturn;
};

export const FormProvider = (props: FormProviderProps) => {
  const { children, ...data } = props;

  return <FormContext.Provider value={data}>{children}</FormContext.Provider>;
};
