import { useMemo } from 'react';

import { css } from '@emotion/react';

import { PERIOD } from '@constants';

import { getNextYear, getToday } from '@utils';
import { compareDateTime } from '@utils/dates';

import type { DateYMD, StudyDetail } from '@custom-types';

import { useFormContext } from '@hooks/useForm';

import Input from '@shared/input/Input';
import Label from '@shared/label/Label';
import MetaBox from '@shared/meta-box/MetaBox';

type PeriodProps = {
  originalStartDate?: StudyDetail['startDate'];
  originalEndDate?: StudyDetail['endDate'];
};

const Period: React.FC<PeriodProps> = ({ originalStartDate, originalEndDate }) => {
  const today = useMemo(() => getToday(), []);

  const minStartDate = originalStartDate ? compareDateTime(originalStartDate, today) : today;
  const maxStartDate = getNextYear(originalStartDate ? compareDateTime(originalStartDate, today, 'max') : today);

  const minEndDate = originalEndDate ? compareDateTime(originalEndDate, today) : today;
  const maxEndDate = getNextYear(originalEndDate ? compareDateTime(originalEndDate, today, 'max') : today);

  return (
    <MetaBox>
      <MetaBox.Title>스터디 운영 기간</MetaBox.Title>
      <MetaBox.Content>
        <StudyStartDateField defaultValue={originalStartDate ?? today} min={minStartDate} max={maxStartDate} />
        <StudyEndDateField defaultValue={originalEndDate ?? ''} min={minEndDate} max={maxEndDate} />
      </MetaBox.Content>
    </MetaBox>
  );
};

type StudyStartDateFieldProps = {
  defaultValue: string;
  min: DateYMD;
  max: DateYMD;
};
const StudyStartDateField: React.FC<StudyStartDateFieldProps> = ({ defaultValue, min, max }) => {
  const { register } = useFormContext();
  const style = css`
    margin-bottom: 12px;
  `;
  return (
    <div css={style}>
      <Label htmlFor={PERIOD.START_DATE}>*스터디 시작 :</Label>
      <Input
        type="date"
        id={PERIOD.START_DATE}
        defaultValue={defaultValue}
        {...register(PERIOD.START_DATE, {
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
};
const StudyEndDateField: React.FC<StudyEndDateFieldProps> = ({ defaultValue, min, max }) => {
  const { register } = useFormContext();
  return (
    <div>
      <Label htmlFor={PERIOD.END_DATE}>스터디 종료 :</Label>
      <Input
        type="date"
        id={PERIOD.END_DATE}
        defaultValue={defaultValue}
        {...register(PERIOD.END_DATE, {
          min,
          max,
        })}
      />
    </div>
  );
};

export default Period;
