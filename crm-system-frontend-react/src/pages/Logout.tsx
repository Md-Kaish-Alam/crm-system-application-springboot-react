import { useDispatch, useSelector } from "react-redux";

import { logout } from "@/features/auth/authSlice";
import { Button } from "@/components/ui/button";
import { AppDispatch, RootState } from "@/redux/store";

const Logout = () => {
  const dispatch = useDispatch<AppDispatch>();
  const token = useSelector((state: RootState) => state.auth.token);

  const handleLogout = () => {
    if (token) {
      dispatch(logout(token));
    }
  };
  return <Button onClick={handleLogout}>Logout</Button>;
};

export default Logout;
