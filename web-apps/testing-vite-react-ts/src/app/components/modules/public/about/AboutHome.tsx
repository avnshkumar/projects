import { Outlet } from "react-router";

const AboutHome = () => {
  return (
    <div>
      <h1>About Home</h1>
      <p>This is the about home page.</p>
      <Outlet />
    </div>
  );
};

export default AboutHome;
