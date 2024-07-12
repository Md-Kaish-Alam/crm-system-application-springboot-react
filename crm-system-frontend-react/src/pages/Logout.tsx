import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

import { clearAuthState, logout } from "@/features/auth/authSlice";
import { Button } from "@/components/ui/button";
import { AppDispatch } from "@/redux/store";

const Logout = () => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  const handleLogout = () => {
    dispatch(logout());
    dispatch(clearAuthState());
    navigate("/singin");
  };
  return <Button onClick={handleLogout}>Logout</Button>;
};

export default Logout;
