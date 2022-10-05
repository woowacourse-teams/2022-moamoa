import { Link } from 'react-router-dom';

import { Theme, css, useTheme } from '@emotion/react';

import { PATH } from '@constants';

import { yyyymmddTommdd } from '@utils';

import type { DateYMD, StudyDetail, StudyId, UserRole } from '@custom-types';

import { BoxButton } from '@components/button';
import { BoxButtonProps } from '@components/button/box-button/BoxButton';
import Card from '@components/card/Card';
import Flex from '@components/flex/Flex';

export type StudyWideFloatBoxProps = Pick<
  StudyDetail,
  'enrollmentEndDate' | 'currentMemberCount' | 'maxMemberCount' | 'recruitmentStatus'
> & {
  studyId: number;
  userRole?: UserRole;
  onRegisterButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const StudyWideFloatBox: React.FC<StudyWideFloatBoxProps> = ({
  studyId,
  userRole,
  enrollmentEndDate,
  currentMemberCount,
  maxMemberCount,
  recruitmentStatus,
  onRegisterButtonClick: handleRegisterButtonClick,
}) => {
  const theme = useTheme();
  const isOpen = recruitmentStatus === 'RECRUITMENT_START';

  return (
    <Card backgroundColor={theme.colors.white} shadow custom={{ padding: '20px' }}>
      <Flex justifyContent="space-between" alignItems="center">
        <div>
          <Card.Heading custom={{ fontSize: theme.fontSize.xl }}>
            <EnrollmentEndDate
              theme={theme}
              isOpen={isOpen}
              userRole={userRole}
              enrollmentEndDate={enrollmentEndDate}
            />
          </Card.Heading>
          <Card.Content custom={{ fontSize: theme.fontSize.md }} maxLine={1}>
            <NumberOfApplicants currentMemberCount={currentMemberCount} maxMemberCount={maxMemberCount} />
          </Card.Content>
        </div>
        {userRole === 'MEMBER' || userRole === 'OWNER' ? (
          <GoToStudyRoomLinkButton theme={theme} studyId={studyId} />
        ) : (
          <RegisterButton theme={theme} disabled={!isOpen} onClick={handleRegisterButtonClick} />
        )}
      </Flex>
    </Card>
  );
};

type EnrollmentEndDateProps = {
  theme: Theme;
  isOpen: boolean;
  userRole?: UserRole;
  enrollmentEndDate?: DateYMD;
};
const EnrollmentEndDate: React.FC<EnrollmentEndDateProps> = ({ theme, isOpen, userRole, enrollmentEndDate }) => {
  if (userRole === 'MEMBER' || userRole === 'OWNER') {
    return <AlreadyRegistered />;
  }

  if (!isOpen) {
    return <Closed />;
  }

  if (!enrollmentEndDate) {
    return <Open />;
  }

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

const AlreadyRegistered = () => <span>이미 가입한 스터디입니다</span>;

const Closed = () => <span>모집 마감</span>;

const Open = () => <span>모집중</span>;

type NumberOfApplicantsProps = {
  currentMemberCount: number;
  maxMemberCount?: number;
};
const NumberOfApplicants: React.FC<NumberOfApplicantsProps> = ({ currentMemberCount, maxMemberCount }) => (
  <Flex justifyContent="space-between">
    <span>모집인원</span>
    <span>
      {currentMemberCount} / {maxMemberCount ?? '∞'}
    </span>
  </Flex>
);

type GoToStudyRoomLinkButtonProps = {
  theme: Theme;
  studyId: StudyId;
};
const GoToStudyRoomLinkButton: React.FC<GoToStudyRoomLinkButtonProps> = ({ theme, studyId }) => (
  <Link to={PATH.STUDY_ROOM(studyId)}>
    <BoxButton type="button" custom={{ fontSize: theme.fontSize.lg }}>
      스터디 방으로 이동하기
    </BoxButton>
  </Link>
);

type RegisterButtonProps = { theme: Theme } & Pick<BoxButtonProps, 'disabled' | 'onClick'>;
const RegisterButton: React.FC<RegisterButtonProps> = ({ theme, disabled: isOpen, onClick: handleClick }) => (
  <BoxButton type="submit" disabled={!isOpen} onClick={handleClick} custom={{ fontSize: theme.fontSize.lg }}>
    {isOpen ? '스터디 가입하기' : '모집이 마감되었습니다'}
  </BoxButton>
);

export default StudyWideFloatBox;
