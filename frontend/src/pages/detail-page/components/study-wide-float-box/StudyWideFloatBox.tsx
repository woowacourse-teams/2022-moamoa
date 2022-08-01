import { css } from '@emotion/react';

import { yyyymmddTommdd } from '@utils/index';

import { StudyDetail } from '@custom-types/index';

import Button from '@components/button/Button';

import * as S from '@detail-page/components/study-wide-float-box/StudyWideFloatBox.style';

// TODO: 스터디에 가입한 사람인지 아닌지 상태도 받아야 함
export type StudyWideFloatBoxProps = Pick<
  StudyDetail,
  'enrollmentEndDate' | 'currentMemberCount' | 'maxMemberCount' | 'recruitmentStatus'
> & {
  handleRegisterBtnClick: React.MouseEventHandler<HTMLButtonElement>;
};

const StudyWideFloatBox: React.FC<StudyWideFloatBoxProps> = ({
  enrollmentEndDate,
  currentMemberCount,
  maxMemberCount,
  recruitmentStatus,
  handleRegisterBtnClick,
}) => {
  const isOpen = recruitmentStatus === 'RECRUITMENT_START';

  return (
    <S.StudyWideFloatBox>
      <S.StudyInfo>
        <S.EnrollmentEndDate>
          {isOpen ? (
            <>
              <span>{yyyymmddTommdd(enrollmentEndDate)}</span>
              까지 가입 가능
            </>
          ) : (
            <span>모집 마감</span>
          )}
        </S.EnrollmentEndDate>
        <S.MemberCount>
          <span>모집인원</span>
          <span>
            {currentMemberCount} / {maxMemberCount}
          </span>
        </S.MemberCount>
      </S.StudyInfo>
      <div>
        <Button
          css={css`
            height: 100%;
            padding: 0 20px;
          `}
          fluid={true}
          disabled={!isOpen}
          onClick={handleRegisterBtnClick}
        >
          {isOpen ? '가입하기' : '모집 마감'}
        </Button>
      </div>
    </S.StudyWideFloatBox>
  );
};

export default StudyWideFloatBox;
