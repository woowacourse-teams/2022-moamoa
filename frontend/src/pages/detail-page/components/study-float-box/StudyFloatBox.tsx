import { Link } from 'react-router-dom';

import { type Theme, css, useTheme } from '@emotion/react';

import { PATH, RECRUITMENT_STATUS } from '@constants';

import { yyyymmddTommdd } from '@utils';

import type { DateYMD, StudyDetail, StudyId } from '@custom-types';

import { BoxButton, type BoxButtonProps } from '@shared/button';
import Card from '@shared/card/Card';
import Flex from '@shared/flex/Flex';

export type StudyFloatBoxProps = Pick<
  StudyDetail,
  'enrollmentEndDate' | 'currentMemberCount' | 'maxMemberCount' | 'recruitmentStatus'
> & {
  studyId: number;
  isOwnerOrMember: boolean;
  ownerName: string;
  onRegisterButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const StudyFloatBox: React.FC<StudyFloatBoxProps> = ({
  studyId,
  isOwnerOrMember,
  enrollmentEndDate,
  currentMemberCount,
  maxMemberCount,
  ownerName,
  recruitmentStatus,
  onRegisterButtonClick: handleRegisterButtonClick,
}) => {
  const theme = useTheme();
  const isOpen = recruitmentStatus === RECRUITMENT_STATUS.START;

  return (
    <Card backgroundColor={theme.colors.white} shadow custom={{ padding: '40px', gap: '8px' }}>
      <Card.Heading custom={{ fontSize: 'xl', marginBottom: '10px' }}>
        {(() => {
          if (isOwnerOrMember) return <AlreadyRegistered />;
          if (!isOpen) return <Closed />;
          if (!enrollmentEndDate) return <Open />;
          return <EnrollmentEndDate theme={theme} enrollmentEndDate={enrollmentEndDate} />;
        })()}
      </Card.Heading>
      <Card.Content custom={{ fontSize: 'lg' }}>
        <NumberOfApplicants currentMemberCount={currentMemberCount} maxMemberCount={maxMemberCount} />
        <StudyOwner ownerName={ownerName} />
        {isOwnerOrMember || !isOpen ? ( // @TODO: 가입하기 버튼을 분리한다(가입안한 사람이 구경할 수 있도록 하기 위함)
          <StudyRoomLink studyId={studyId} />
        ) : (
          <RegisterButton onClick={handleRegisterButtonClick} />
        )}
      </Card.Content>
    </Card>
  );
};

export default StudyFloatBox;

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

const AlreadyRegistered = () => <span>이미 가입한 스터디입니다</span>;

const Closed = () => <span>모집 마감</span>;

const Open = () => <span>모집중</span>;

type StudyRoomLinkProps = {
  studyId: StudyId;
};
const StudyRoomLink: React.FC<StudyRoomLinkProps> = ({ studyId }) => (
  <Link to={PATH.STUDY_ROOM(studyId)}>
    <BoxButton type="button" custom={{ fontSize: 'lg' }}>
      스터디 방으로 이동하기
    </BoxButton>
  </Link>
);

type RegisterButtonProps = Pick<BoxButtonProps, 'onClick'>;
const RegisterButton: React.FC<RegisterButtonProps> = ({ onClick: handleClick }) => (
  <BoxButton type="submit" onClick={handleClick} custom={{ fontSize: 'lg' }}>
    스터디 가입하기
  </BoxButton>
);

type NumberOfApplicantsProps = {
  currentMemberCount: number;
  maxMemberCount?: number;
};
const NumberOfApplicants: React.FC<NumberOfApplicantsProps> = ({ currentMemberCount, maxMemberCount }) => (
  <Flex justifyContent="space-between" custom={{ marginBottom: '8px' }}>
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
  <Flex justifyContent="space-between" custom={{ marginBottom: '16px' }}>
    <span>스터디장</span>
    <span>{ownerName}</span>
  </Flex>
);
