import { Outlet } from "react-router";
import { Flex } from "@radix-ui/themes";

const AppHome = () => {
  return (
    <div className='block w-[100vw] h-[100vh]'>
      <Flex direction='column'>
        <Outlet />
      </Flex>
    </div>
  );
};

export default AppHome;
