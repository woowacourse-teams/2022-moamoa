const nums = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
const arrows = ['ArrowLeft', 'ArrowRight', 'ArrowDown', 'ArrowUp'];

const usePositiveNumberInput = () => {
  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    const { key } = e;
    if (key !== 'Backspace' && !nums.includes(key) && !arrows.includes(key)) {
      e.preventDefault();
      return;
    }
  };
  return { handleKeyDown };
};

export default usePositiveNumberInput;
