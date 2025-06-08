import { useRef, useState } from "react";
import { Button } from "@radix-ui/themes";

const RefLetState = () => {
  const [state, setState] = useState(0);
  const ref = useRef(0);
  let letState = 0;

  const incrementState = () => {
    letState += 1;
    setState((prev: number) => prev + 1);
  };
  // letState += 1;
  ref.current += 1;

  console.log(ref.current, letState, state);
  const incrementRef = () => {
    ref.current += 1;
  };
  const incrementLet = () => {
    letState += 1;
  };

  return (
    <div>
      <h2>Ref vs State</h2>
      <p>State: {state}</p>
      <p>Ref: {ref.current}</p>
      <p>Let: {letState}</p>
      <Button onClick={incrementState}>Increment State</Button>
      <Button onClick={incrementRef}>Increment Ref</Button>
      <Button onClick={incrementLet}>Increment Let</Button>
    </div>
  );
};

export default RefLetState;
