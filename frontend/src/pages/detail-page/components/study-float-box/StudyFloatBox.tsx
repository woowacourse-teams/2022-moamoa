import { Link } from 'react-router-dom';

import { type Theme, css, useTheme } from '@emotion/react';

import { PATH } from '@constants';

import { yyyymmddTommdd } from '@utils';

import { type DateYMD, type StudyDetail, type StudyId, type UserRole } from '@custom-types';

import { BoxButton } from '@components/button';
import { BoxButtonProps } from '@components/button/box-button/BoxButton';
import Card from '@components/card/Card';
import Flex from '@components/flex/Flex';

export type StudyFloatBoxProps = Pick<
  StudyDetail,
  'enrollmentEndDate' | 'currentMemberCount' | 'maxMemberCount' | 'recruitmentStatus'
> & {
  studyId: number;
  userRole?: UserRole;
  ownerName: string;
  onRegisterButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const StudyFloatBox: React.FC<StudyFloatBoxProps> = ({
  studyId,
  userRole,
  enrollmentEndDate,
  currentMemberCount,
  maxMemberCount,
  ownerName,
  recruitmentStatus,
  onRegisterButtonClick: handleRegisterButtonClick,
}) => {
  const theme = useTheme();
  const isOpen = recruitmentStatus === 'RECRUITMENT_START';

  return (
    <Card backgroundColor={theme.colors.white} shadow custom={{ padding: '40px', gap: '8px' }}>
      <Card.Heading custom={{ fontSize: 'xl', marginBottom: '10px' }}>
        <EnrollmentEndDate theme={theme} isOpen={isOpen} userRole={userRole} enrollmentEndDate={enrollmentEndDate} />
      </Card.Heading>
      <Card.Content custom={{ fontSize: 'lg' }}>
        <NumberOfApplicants currentMemberCount={currentMemberCount} maxMemberCount={maxMemberCount} />
        <StudyOwner ownerName={ownerName} />
        {userRole === 'MEMBER' || userRole === 'OWNER' ? (
          <GoToStudyRoomLinkButton theme={theme} studyId={studyId} />
        ) : (
          <RegisterButton theme={theme} disabled={!isOpen} onClick={handleRegisterButtonClick} />
        )}
      </Card.Content>
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
    return <span>이미 가입한 스터디입니다</span>;
  }

  if (!isOpen) {
    return <span>모집 마감</span>;
  }

  if (!enrollmentEndDate) {
    return <span>모집중</span>;
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

type GoToStudyRoomLinkButtonProps = {
  theme: Theme;
  studyId: StudyId;
};
const GoToStudyRoomLinkButton: React.FC<GoToStudyRoomLinkButtonProps> = ({ theme, studyId }) => (
  <Link to={PATH.STUDY_ROOM(studyId)}>
    <BoxButton type="button" custom={{ fontSize: 'lg' }}>
      스터디 방으로 이동하기
    </BoxButton>
  </Link>
);

type RegisterButtonProps = { theme: Theme } & Pick<BoxButtonProps, 'disabled' | 'onClick'>;
const RegisterButton: React.FC<RegisterButtonProps> = ({ theme, disabled: isOpen, onClick: handleClick }) => (
  <BoxButton type="submit" disabled={!isOpen} onClick={handleClick} custom={{ fontSize: 'lg' }}>
    {isOpen ? '스터디 가입하기' : '모집이 마감되었습니다'}
  </BoxButton>
);

type NumberOfApplicantsProps = {
  currentMemberCount: number;
  maxMemberCount?: number;
};
const NumberOfApplicants: React.FC<NumberOfApplicantsProps> = ({ currentMemberCount, maxMemberCount }) => (
  <Flex justifyContent="space-between" custom={{ marginBottom: '30px' }}>
    <span>모집인원</span>
    <span>
      {currentMemberCount} / {maxMemberCount ?? '∞'}
    </span>
  </Flex>
);

type StudyOwnerProps = {
  ownerName: string;
};
const StudyOwner: React.FC<StudyOwnerProps> = ({ ownerName }) => (
  <Flex justifyContent="space-between" custom={{ marginBottom: '20px' }}>
    <span>스터디장</span>
    <span>{ownerName}</span>
  </Flex>
);

export default StudyFloatBox;
