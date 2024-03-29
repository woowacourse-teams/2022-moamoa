import { Link } from 'react-router-dom';

import { type Theme, css, useTheme } from '@emotion/react';

import { PATH, RECRUITMENT_STATUS } from '@constants';

import { yyyymmddTommdd } from '@utils';

import type { DateYMD, StudyDetail, StudyId } from '@custom-types';

import { BoxButton, type BoxButtonProps } from '@shared/button';
import Card from '@shared/card/Card';
import Flex from '@shared/flex/Flex';

export type StudyWideFloatBoxProps = Pick<
  StudyDetail,
  'enrollmentEndDate' | 'currentMemberCount' | 'maxMemberCount' | 'recruitmentStatus'
> & {
  studyId: number;
  isOwnerOrMember: boolean;
  onRegisterButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const StudyWideFloatBox: React.FC<StudyWideFloatBoxProps> = ({
  studyId,
  isOwnerOrMember,
  enrollmentEndDate,
  currentMemberCount,
  maxMemberCount,
  recruitmentStatus,
  onRegisterButtonClick: handleRegisterButtonClick,
}) => {
  const theme = useTheme();
  const isOpen = recruitmentStatus === RECRUITMENT_STATUS.START;

  return (
    <Card backgroundColor={theme.colors.white} shadow custom={{ padding: '20px' }}>
      <Flex justifyContent="space-between" alignItems="center">
        <div>
          <Card.Heading custom={{ fontSize: 'xl' }}>
            {(() => {
              if (isOwnerOrMember) return <AlreadyRegistered />;
              if (!isOpen) return <Closed />;
              if (!enrollmentEndDate) return <Open />;
              return <EnrollmentEndDate theme={theme} enrollmentEndDate={enrollmentEndDate} />;
            })()}
          </Card.Heading>
          <Card.Content custom={{ fontSize: 'md' }} maxLine={1}>
            <NumberOfApplicants currentMemberCount={currentMemberCount} maxMemberCount={maxMemberCount} />
          </Card.Content>
        </div>
        <div>
          {isOwnerOrMember || !isOpen ? (
            <StudyRoomLink studyId={studyId} />
          ) : (
            <RegisterButton onClick={handleRegisterButtonClick} />
          )}
        </div>
      </Flex>
    </Card>
  );
};

export default StudyWideFloatBox;

const AlreadyRegistered = () => <span>이미 가입한 스터디입니다</span>;

const Closed = () => <span>모집 마감</span>;

const Open = () => <span>모집중</span>;

type EnrollmentEndDateProps = {
  theme: Theme;
  enrollmentEndDate: DateYMD;
};
const EnrollmentEndDate: React.FC<EnrollmentEndDateProps> = ({ theme, enrollmentEndDate }) => {
  return (
    <>
      <span>{yyyymmddTommdd(enrollmentEndDate)}</span>
      <span
        css={css`
          font-size: ${theme.fontSize.lg};
        `}
      >
        까지 가입 가능
      </span>
    </>
  );
};

type NumberOfApplicantsProps = {
  currentMemberCount: number;
  maxMemberCount?: number;
};
const NumberOfApplicants: React.FC<NumberOfApplicantsProps> = ({ currentMemberCount, maxMemberCount }) => (
  <>
    <span
      css={css`
        margin-right: 16px;
      `}
    >
      모집인원
    </span>
    <span>
      {currentMemberCount} / {maxMemberCount ?? '∞'}
    </span>
  </>
);

type StudyRoomLinkProps = {
  studyId: StudyId;
};
const StudyRoomLink: React.FC<StudyRoomLinkProps> = ({ studyId }) => (
  <Link to={PATH.STUDY_ROOM(studyId)}>
    <BoxButton type="button" custom={{ fontSize: 'lg' }}>
      이동하기
    </BoxButton>
  </Link>
);

type RegisterButtonProps = Pick<BoxButtonProps, 'onClick'>;
const RegisterButton: React.FC<RegisterButtonProps> = ({ onClick: handleClick }) => (
  <BoxButton type="submit" onClick={handleClick} custom={{ fontSize: 'lg' }}>
    가입하기
  </BoxButton>
);
