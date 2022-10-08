import { useMemo } from 'react';

import { css } from '@emotion/react';

import { getNextYear, getToday } from '@utils';
import { compareDateTime } from '@utils/dates';

import { type DateYMD, type StudyDetail } from '@custom-types';

import { type UseFormRegister, useFormContext } from '@hooks/useForm';

import Input from '@components/input/Input';
import Label from '@components/label/Label';
import MetaBox from '@components/meta-box/MetaBox';

type PeriodProps = {
  originalStartDate?: StudyDetail['startDate'];
  originalEndDate?: StudyDetail['endDate'];
};

const START_DATE = 'start-date';
const END_DATE = 'end-date';

const Period: React.FC<PeriodProps> = ({ originalStartDate, originalEndDate }) => {
  const { register } = useFormContext();

  const today = useMemo(() => getToday(), []);

  const minStartDate = originalStartDate ? compareDateTime(originalStartDate, today) : today;
  const maxStartDate = getNextYear(originalStartDate ? compareDateTime(originalStartDate, today, 'max') : today);

  const minEndDate = originalEndDate ? compareDateTime(originalEndDate, today) : today;
  const maxEndDate = getNextYear(originalEndDate ? compareDateTime(originalEndDate, today, 'max') : today);

  return (
    <MetaBox>
      <MetaBox.Title>스터디 운영 기간</MetaBox.Title>
      <MetaBox.Content>
        <StudyStartDateField
          defaultValue={originalStartDate ?? today}
          min={minStartDate}
          max={maxStartDate}
          register={register}
        />
        <StudyEndDateField defaultValue={originalEndDate ?? ''} min={minEndDate} max={maxEndDate} register={register} />
      </MetaBox.Content>
    </MetaBox>
  );
};

type StudyStartDateFieldProps = {
  defaultValue: string;
  min: DateYMD;
  max: DateYMD;
  register: UseFormRegister;
};
const StudyStartDateField: React.FC<StudyStartDateFieldProps> = ({ defaultValue, min, max, register }) => {
  const style = css`
    margin-bottom: 12px;
  `;
  return (
    <div css={style}>
      <Label htmlFor={START_DATE}>*스터디 시작 :</Label>
      <Input
        type="date"
        id={START_DATE}
        defaultValue={defaultValue}
        {...register(START_DATE, {
          min,
          max,
          required: true,
        })}
      />
    </div>
  );
};

type StudyEndDateFieldProps = {
  defaultValue: string;
  min: DateYMD;
  max: DateYMD;
  register: UseFormRegister;
};
const StudyEndDateField: React.FC<StudyEndDateFieldProps> = ({ defaultValue, min, max, register }) => {
  return (
    <div>
      <Label htmlFor={END_DATE}>스터디 종료 :</Label>
      <Input
        type="date"
        id={END_DATE}
        defaultValue={defaultValue}
        {...register(END_DATE, {
          min,
          max,
        })}
      />
    </div>
  );
};

export default Period;
