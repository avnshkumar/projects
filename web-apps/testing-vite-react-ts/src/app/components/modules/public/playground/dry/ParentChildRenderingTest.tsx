import { Button } from "@radix-ui/themes";
import { useRef, useState } from "react";

const ParentChildRenderingTest = () => {
  const [counter, setCounter] = useState(0);
  const ref = useRef(0);
  ref.current += 1;

  return (
    <div>
      <Button onClick={() => setCounter(counter + 1)}>Increase counter</Button>
      <p>Counter: {counter}</p>
      <p>Parent Ref: {ref.current}</p>
      <ChildWithProp counter={counter} />
      <ChildWithoutProp />
    </div>
  );
};

const ChildWithoutProp = () => {
  const [myState, setMyState] = useState(0);
  const ref = useRef(0);

  ref.current += 1;

  return (
    <div>
      <Button onClick={() => setMyState(myState + 1)}>Increase state</Button>
      <p>ChildWithoutProp State: {myState}</p>
      <p>ChildWithout PropRef: {ref.current}</p>
    </div>
  );
};

const ChildWithProp = ({ counter }: { counter: number }) => {
  const [myState, setMyState] = useState(0);
  const ref = useRef(0);

  ref.current += 1;

  return (
    <div>
      <Button onClick={() => setMyState(myState + 1)}>Increase state</Button>
      <p>ChildWithProp State: {myState}</p>
      <p>Parent Counter: {counter}</p>
      <p>ChildWithProp Ref: {ref.current}</p>
    </div>
  );
};

export default ParentChildRenderingTest;
