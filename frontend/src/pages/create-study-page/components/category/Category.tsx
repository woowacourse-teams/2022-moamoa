import * as S from '@create-study-page/components/category/Category.style';
import MetaBox from '@create-study-page/components/meta-box/MetaBox';

import { css } from '@emotion/react';

type CategoryProps = {
  className?: string;
};

const Category = ({ className }: CategoryProps) => {
  return (
    <S.Category className={className}>
      <MetaBox>
        <MetaBox.Title>스터디 분류</MetaBox.Title>
        <MetaBox.Content>
          {/* TODO: Perfect Number Input사용하기  */}
          <div
            css={css`
              margin-bottom: 6px;
            `}
          >
            <label
              css={css`
                margin-right: 10px;
              `}
              htmlFor="max-member-count"
            >
              기수 :
            </label>
            <select defaultValue={'4기'}>
              <option value="4기">4기</option>
              <option value="3기">3기</option>
              <option value="2기">2기</option>
              <option value="1기">1기</option>
            </select>
          </div>
          <div
            css={css`
              display: flex;
            `}
          >
            <span
              css={css`
                margin-right: 10px;
              `}
            >
              영역 :
            </span>
            <div
              css={css`
                display: flex;
                margin-right: 8px;
              `}
            >
              <input
                css={css`
                  margin-right: 4px;
                `}
                type="checkbox"
                id="area-fe"
                name="area"
                value="fe"
              />
              <label htmlFor="area-fe">FE</label>
            </div>
            <div
              css={css`
                display: flex;
                margin-right: 8px;
              `}
            >
              <input
                css={css`
                  margin-right: 4px;
                `}
                type="checkbox"
                id="area-be"
                name="area"
                value="be"
              />
              <label htmlFor="area-be">BE</label>
            </div>
          </div>
        </MetaBox.Content>
      </MetaBox>
    </S.Category>
  );
};

export default Category;
