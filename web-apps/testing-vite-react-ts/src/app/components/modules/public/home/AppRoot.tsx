import { Outlet } from "react-router";
import AppRootNavigation from "./AppRootNavigation.tsx";
import { Flex } from "@radix-ui/themes";

const AppRoot = () => {
  return (
    <div className='block w-[100vw] h-[100vh]'>
      <Flex direction='column'>
        <AppRootNavigation />
        <Outlet />
      </Flex>
    </div>
  );
};

export default AppRoot;
