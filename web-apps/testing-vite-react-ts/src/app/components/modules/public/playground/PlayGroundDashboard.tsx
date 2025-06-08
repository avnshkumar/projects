import RefLetState from "./dry/RefLetState.tsx";
import ParentChildRenderingTest from "./dry/ParentChildRenderingTest.tsx";
import ZomatoReceipt from "./dry/ZomatoReceipt.tsx";
import { Tabs } from "@radix-ui/themes";
import CheckBackendHealth from "./dry/CheckBackendHealth.tsx";

const PlayGroundDashboard = () => {
  return (
    <div>
      <h1>PlayGround Dashboard</h1>
      <p>Welcome to the PlayGround Dashboard!</p>
      <Tabs.Root defaultValue='tab1' orientation='vertical'>
        <Tabs.List aria-label='tabs example'>
          <Tabs.Trigger value='tab1'>Ref vs Let vs State</Tabs.Trigger>
          <Tabs.Trigger value='tab2'>Parent Child Rendering Test</Tabs.Trigger>
          <Tabs.Trigger value='tab3'>Zomato Receipts</Tabs.Trigger>
          <Tabs.Trigger value='tab4'>Check Backend Health</Tabs.Trigger>
        </Tabs.List>
        <Tabs.Content value='tab1'>
          <RefLetState />
        </Tabs.Content>
        <Tabs.Content value='tab2'>
          <ParentChildRenderingTest />
        </Tabs.Content>
        <Tabs.Content value='tab3'>
          <ZomatoReceipt />
        </Tabs.Content>
        <Tabs.Content value='tab4'>
           <CheckBackendHealth />
        </Tabs.Content>
      </Tabs.Root>
    </div>
  );
};
export default PlayGroundDashboard;
