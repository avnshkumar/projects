import { Theme, ThemePanel, ThemeProps } from "@radix-ui/themes";
import { ReactNode } from "react";
const ThemeProvider = ({
  children,
  themeOptions,
}: {
  children: ReactNode;
  themeOptions: ThemeProps;
}) => {
  return (
    <Theme
      accentColor={themeOptions.accentColor}
      grayColor={themeOptions.grayColor}
      radius='large'
      scaling='95%'
    >
      {children}
      <ThemePanel />
    </Theme>
  );
};

export default ThemeProvider;
