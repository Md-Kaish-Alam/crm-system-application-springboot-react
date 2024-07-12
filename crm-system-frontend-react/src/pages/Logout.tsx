import { useDispatch } from "react-redux";

import { clearAuthState, logout } from "@/features/auth/authSlice";
import { Button } from "@/components/ui/button";
import { AppDispatch } from "@/redux/store";

const Logout = () => {
  const dispatch = useDispatch<AppDispatch>();

  const handleLogout = () => {
    dispatch(logout());
    dispatch(clearAuthState());
  };
  return <Button onClick={handleLogout}>Logout</Button>;
};

export default Logout;
