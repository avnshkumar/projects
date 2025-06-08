import {
  NavigationMenu,
  NavigationMenuItem,
  NavigationMenuList,
  NavigationMenuSub,
} from "@radix-ui/react-navigation-menu";
import { HomeIcon, MoonIcon } from "@radix-ui/react-icons";
import { Button, Flex } from "@radix-ui/themes";
import { useNavigate } from "react-router";

const AppRootNavigation = () => {
  const navigate = useNavigate();
  const handlePlaygroundClick = () => {
    navigate("/playground");
  };
  const handleHomeClick = () => {
    navigate("/");
  };

  const switchTheme = () => {};

  return (
    <Flex className='w-full justify-between'>
      <NavigationMenu className='flex w-full justify-between'>
        <NavigationMenuSub>
          <NavigationMenuList>
            <NavigationMenuItem>
              <Button onClick={handleHomeClick}>
                <HomeIcon />
              </Button>
            </NavigationMenuItem>
          </NavigationMenuList>
        </NavigationMenuSub>
        <NavigationMenuSub>
          <NavigationMenuList className='flex'>
            <NavigationMenuItem>
              <Button onClick={handlePlaygroundClick}>Playground</Button>
            </NavigationMenuItem>
            <NavigationMenuItem>
              <Button variant='soft' onClick={switchTheme}>
                <MoonIcon />
              </Button>
            </NavigationMenuItem>
          </NavigationMenuList>
        </NavigationMenuSub>
      </NavigationMenu>
    </Flex>
  );
};

export default AppRootNavigation;
