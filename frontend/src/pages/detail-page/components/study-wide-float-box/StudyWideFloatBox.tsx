import { css } from '@emotion/react';

import { yyyymmddTommdd } from '@utils/index';

import Button from '@components/button/Button';

import * as S from '@detail-page/components/study-wide-float-box/StudyWideFloatBox.style';

export type StudyWideFloatBoxProps = {
  studyId: number;
  deadline: string;
  currentMemberCount: number;
  maxMemberCount: number;
  status: 'OPEN' | 'CLOSE'; // TODO: 스터디에 가입한 사람인지 아닌지 상태도 받아야 함
  handleRegisterBtnClick: (studyId: number) => React.MouseEventHandler<HTMLButtonElement>;
};

const StudyWideFloatBox: React.FC<StudyWideFloatBoxProps> = ({
  studyId,
  deadline,
  currentMemberCount,
  maxMemberCount,
  status,
  handleRegisterBtnClick,
}) => {
  const isOpen = status === 'OPEN';

  return (
    <S.StudyWideFloatBox>
      <S.StudyInfo>
        <S.Deadline>
          {isOpen ? (
            <>
              <span>{yyyymmddTommdd(deadline)}</span>
              까지 가입 가능
            </>
          ) : (
            <span>모집 마감</span>
          )}
        </S.Deadline>
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
          onClick={handleRegisterBtnClick(studyId)}
        >
          {isOpen ? '가입하기' : '모집 마감'}
        </Button>
      </div>
    </S.StudyWideFloatBox>
  );
};

export default StudyWideFloatBox;
