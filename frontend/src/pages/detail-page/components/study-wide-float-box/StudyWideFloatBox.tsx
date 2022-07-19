import { css } from '@emotion/react';

import yyyymmddTommdd from '@utils/yyyymmddTommdd';

import Button from '@components/button/Button';

import * as S from './StudyWideFloatBox.style';

export type StudyWideFloatBoxProps = {
  studyId: string;
  deadline: string;
  currentMemberCount: number;
  maxMemberCount: number;
  owner: string;
  onClickRegisterBtn: (studyId: string) => void;
};

const StudyWideFloatBox: React.FC<StudyWideFloatBoxProps> = ({
  studyId,
  deadline,
  currentMemberCount,
  maxMemberCount,
  owner,
  onClickRegisterBtn,
}) => {
  const handleClickRegisterBtn = () => onClickRegisterBtn(studyId);

  return (
    <S.StudyWideFloatBox>
      <div className="left">
        <div className="deadline">
          <strong>{yyyymmddTommdd(deadline)}</strong>
          까지 가입 가능
        </div>
        <div className="member-count">
          <span>모집인원</span>
          <span>
            {currentMemberCount} / {maxMemberCount}
          </span>
        </div>
      </div>
      <div className="right">
        <Button
          css={css`
            height: 100%;
            padding: 0 20px;
          `}
          fluid={false}
          onClick={handleClickRegisterBtn}
        >
          가입
        </Button>
      </div>
    </S.StudyWideFloatBox>
  );
};

export default StudyWideFloatBox;
