/** @type {import('tailwindcss').Config} */

const arr0_to_100 = [...Array(101).keys()];
const px0_50 = { ...Array.from(Array(51)).map((_, i) => `${i}px`) };
const px0_100 = { ...Array.from(Array(101)).map((_, i) => `${i}px`) };
const px0_500 = { ...Array.from(Array(501)).map((_, i) => `${i}px`) };

module.exports = {
  content: ['src/components/*.tsx', 'src/pages/*.tsx'],
  theme: {
    extend: {
      borderWidth: px0_50,
      fontSize: px0_100,
      lineHeight: px0_100,
      width: px0_500,
      maxWidth: px0_500,
      minWidth: px0_500,
      maxHeight: px0_500,
      minHeight: px0_500,
      spacing: px0_100,
      zIndex: arr0_to_100,
    },
  },
  plugins: [],
};
