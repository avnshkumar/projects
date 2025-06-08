import ThemeProvider from "./ThemeProvider.tsx";
import RouteProvider from "./RouteProvider.tsx";
import { ThemeProps } from "@radix-ui/themes";
const AppProvider = () => {
  const themeOptions: ThemeProps = {
    accentColor: "blue",
    grayColor: "sand",
    radius: "large",
    scaling: "95%",
  };
  return (
    <ThemeProvider themeOptions={themeOptions}>
      <RouteProvider />
    </ThemeProvider>
  );
};

export default AppProvider;
