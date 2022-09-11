// quick style sheet
import { css } from '@emotion/react';

const arr0_to_100 = [...Array(101).keys()];
const arr0_to_500 = [...Array(501).keys()];

const layout = {
  block: 'display: block',
  'inline-block': 'display: inline-block',
  inline: 'display: inline',
  flex: 'display: flex',
  'inline-flex': 'display: inline-flex',
  table: 'display: table',
  'inline-table': 'display: inline-table',
  'table-caption': 'display: table-caption',
  'table-cell': 'display: table-cell',
  'table-column': 'display: table-column',
  'table-column-group': 'display: table-column-group',
  'table-footer-group': 'display: table-footer-group',
  'table-header-group': 'display: table-header-group',
  'table-row-group': 'display: table-row-group',
  'table-row': 'display: table-row',
  'flow-root': 'display: flow-root',
  grid: 'display: grid',
  'inline-grid': 'display: inline-grid',
  contents: 'display: contents',
  'list-item': 'display: list-item',
  hidden: 'display: none',
};

const flex = {
  'flex-1': 'flex: 1 1 0%',
  'flex-auto': 'flex: 1 1 auto',
  'flex-initial': 'flex: 0 1 auto',
  'flex-none': 'flex: none',
  'flex-col': 'flex-direction: column',
  'flex-grow': 'flex-grow: 1',
};

const justifyContent = {
  'justify-start': 'justify-content: flex-start',
  'justify-end': 'justify-content: flex-end',
  'justify-center': 'justify-content: center',
  'justify-between': 'justify-content: space-between',
  'justify-around': 'justify-content: space-around',
  'justify-evenly': 'justify-content: space-evenly',
};

const alignItems = {
  'items-start': 'align-items: flex-start',
  'items-end': 'align-items: flex-end',
  'items-center': 'align-items: center',
  'items-baseline': 'align-items: baseline',
  'items-stretch': 'align-items: stretch',
};

const alignSelf = {
  'self-auto': 'align-self: auto',
  'self-start': 'align-self: flex-start',
  'self-end': 'align-self: flex-end',
  'self-center': 'align-self: center',
  'self-stretch': 'align-self: stretch',
  'self-baseline': 'align-self: baseline',
};

const margin = arr0_to_100.reduce((acc, cur) => {
  acc[`mt-${cur}`] = `margin-top: ${cur}px`;
  acc[`mr-${cur}`] = `margin-right: ${cur}px`;
  acc[`mb-${cur}`] = `margin-bottom: ${cur}px`;
  acc[`ml-${cur}`] = `margin-left: ${cur}px`;
  return acc;
}, {} as Record<string, string>);

const padding = arr0_to_100.reduce((acc, cur) => {
  acc[`p-${cur}`] = `padding-top: ${cur}px; padding-right: ${cur}px; padding-bottom: ${cur}px; padding-left: ${cur}px`;
  acc[`py-${cur}`] = `padding-top: ${cur}px; padding-bottom: ${cur}px`;
  acc[`px-${cur}`] = `padding-left: ${cur}px; padding-right: ${cur}px`;

  acc[`pt-${cur}`] = `padding-top: ${cur}px`;
  acc[`pr-${cur}`] = `padding-right: ${cur}px`;
  acc[`pb-${cur}`] = `padding-bottom: ${cur}px`;
  acc[`pl-${cur}`] = `padding-left: ${cur}px`;
  return acc;
}, {} as Record<string, string>);

const gap = arr0_to_100.reduce((acc, cur) => {
  acc[`gap-x-${cur}`] = `column-gap: ${cur}px`;
  acc[`gap-y-${cur}`] = `row-gap: ${cur}px`;
  return acc;
}, {} as Record<string, string>);

const widthPx = arr0_to_500.reduce((acc, cur) => {
  acc[`w-${cur}`] = `width: ${cur}px`;
  acc[`max-w-${cur}`] = `max-width: ${cur}px`;
  acc[`min-w-${cur}`] = `min-width: ${cur}px`;
  return acc;
}, {} as Record<string, string>);

const width = {
  'w-full': 'width: 100%',
};

const heightPx = arr0_to_500.reduce((acc, cur) => {
  acc[`h-${cur}`] = `height: ${cur}px`;
  acc[`max-h-${cur}`] = `max-height: ${cur}px`;
  acc[`min-h-${cur}`] = `min-height: ${cur}px`;
  return acc;
}, {} as Record<string, string>);

const height = {
  'h-full': 'height: 100%',
};

const position = {
  static: 'position: static',
  fixed: 'position: fixed',
  absolute: 'position: absolute',
  relative: 'position: relative',
  sticky: 'position: sticky',
};

const trbl = arr0_to_100.reduce((acc, cur) => {
  acc[`top-${cur}`] = `top: ${cur}px`;
  acc[`right-${cur}`] = `right: ${cur}px`;
  acc[`bottom-${cur}`] = `bottom: ${cur}px`;
  acc[`left-${cur}`] = `left: ${cur}px`;
  return acc;
}, {} as Record<string, string>);

const zIndex = arr0_to_100.reduce((acc, cur) => {
  acc[`z-${cur}`] = `z-index: ${cur}`;
  return acc;
}, {} as Record<string, string>);

const overflow = {
  'overflow-auto': 'overflow: auto',
  'overflow-hidden': 'overflow: hidden',
  'overflow-clip': 'overflow: clip',
  'overflow-visible': 'overflow: visible',
  'overflow-scroll': 'overflow: scroll',
  'overflow-x-auto': 'overflow-x: auto',
  'overflow-y-auto': 'overflow-y: auto',
  'overflow-x-hidden': 'overflow-x: hidden',
  'overflow-y-hidden': 'overflow-y: hidden',
  'overflow-x-clip': 'overflow-x: clip',
  'overflow-y-clip': 'overflow-y: clip',
  'overflow-x-visible': 'overflow-x: visible',
  'overflow-y-visible': 'overflow-y: visible',
  'overflow-x-scroll': 'overflow-x: scroll',
  'overflow-y-scroll': 'overflow-y: scroll',
};

const textAlign = {
  'text-left': 'text-align: left',
  'text-center': 'text-align: center',
  'text-right': 'text-align: right',
  'text-justify': 'text-align: justify',
  'text-start': 'text-align: start',
  'text-end': 'text-align: end',
};

const borderRadius = arr0_to_100.reduce((acc, cur) => {
  acc[`rounded-${cur}`] = `border-radius: ${cur}px`;
  acc[`rounded-t-${cur}`] = `border-top-left-radius: ${cur}px; border-top-right-radius: ${cur}px`;
  acc[`rounded-r-${cur}`] = `border-top-right-radius: ${cur}px; border-bottom-right-radius: ${cur}px`;
  acc[`rounded-b-${cur}`] = `border-bottom-right-radius: ${cur}px; border-bottom-left-radius: ${cur}px`;
  acc[`rounded-l-${cur}`] = `border-top-left-radius: ${cur}px; border-bottom-left-radius: ${cur}px`;

  return acc;
}, {} as Record<string, string>);

const fontSize = arr0_to_100.reduce((acc, cur) => {
  acc[`text-${cur}`] = `font-size: ${cur}px`;
  return acc;
}, {} as Record<string, string>);

const totalStyles = {
  ...layout,
  ...flex,
  ...justifyContent,
  ...alignItems,
  ...alignSelf,
  ...margin,
  ...padding,
  ...gap,
  ...widthPx,
  ...width,
  ...heightPx,
  ...height,
  ...position,
  ...trbl,
  ...zIndex,
  ...overflow,
  ...textAlign,
  ...borderRadius,
  ...fontSize,
};

const cssPropertyForArbitararyValue = {
  // margin
  mt: ['margin-top'],
  mr: ['margin-right'],
  mb: ['margin-bottom'],
  ml: ['margin-left'],
  m: ['margin-top', 'margin-right', 'margin-bottom', 'margin-left'],
  my: ['margin-top', 'margin-bottom'],
  mx: ['margin-left', 'margin-right'],

  // padding
  pt: ['padding-top'],
  pr: ['padding-right'],
  pb: ['padding-bottom'],
  pl: ['padding-left'],
  p: ['padding-top', 'padding-right', 'padding-bottom', 'padding-left'],
  py: ['padding-top', 'padding-bottom'],
  px: ['padding-left', 'padding-right'],

  // size
  h: ['height'],
  'min-h': ['min-height'],
  'max-h': ['max-height'],
  w: ['width'],
  'max-w': ['max-width'],
  'min-w': ['min-width'],

  // border
  rounded: ['border-radius'],

  // typo
  text: ['font-size'],
};

type styleKey = keyof typeof totalStyles;
type cssShortPropertyKey = keyof typeof cssPropertyForArbitararyValue;

const getArbitararyValue = (str: string) => {
  return str.substring(str.indexOf('[') + 1, str.indexOf(']'));
};

// TODO: Test 코드 만들자
const combine = (template: TemplateStringsArray, args: Array<string>): string => {
  const result = [];
  for (let i = 0; i < template.length - 1; i += 1) {
    result.push(template[i]);
    result.push(args[i]);
  }
  result.push(template[template.length - 1]);
  return result.join('');
};

// TODO: Test 코드 만들다
const tw = (template: TemplateStringsArray, ...args: Array<string>) => {
  let templateStr = template[0];
  if (template.length > 1) templateStr = combine(template, args);

  const styles = templateStr.split(' ');
  const styleText = styles
    .map(s => {
      const arbitararyValue = getArbitararyValue(s);

      if (arbitararyValue) {
        // py-[12px]에서 ['py-', '[12px]']로 짜르고 마지막 요소를 빼낸다
        const shortCssProperty = s.split('-').slice(0, -1).join('-');
        const fullCssProperties = cssPropertyForArbitararyValue[shortCssProperty as cssShortPropertyKey];
        const cssTexts = fullCssProperties.map(fullCssProp => {
          return `${fullCssProp}: ${arbitararyValue}`;
        });
        return cssTexts.join(';');
      }

      if (!(s in totalStyles)) {
        throw new Error('올바른 스타일을 입력해 주세요');
      }
      return totalStyles[s as styleKey];
    })
    .join(';');

  // 마지막 ;도 중요하다!
  return css`
    ${styleText};
  `;
};

export default tw;
