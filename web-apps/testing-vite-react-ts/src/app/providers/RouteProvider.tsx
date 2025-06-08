import { createBrowserRouter, RouterProvider } from "react-router";
import AppHome from "../components/modules/public/home/AppHome.tsx";
import AboutHome from "../components/modules/public/about/AboutHome.tsx";
import PlayGroundDashboard from "../components/modules/public/playground/PlayGroundDashboard.tsx";
import AppRoot from "../components/modules/public/home/AppRoot.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    Component: AppRoot,
    children: [
      { index: true, Component: AppHome },
      { path: "about", Component: AboutHome },
      { path: "playground", Component: PlayGroundDashboard },
    ],
  },
]);
const RouteProvider = () => {
  return <RouterProvider router={router} />;
};

export default RouteProvider;
