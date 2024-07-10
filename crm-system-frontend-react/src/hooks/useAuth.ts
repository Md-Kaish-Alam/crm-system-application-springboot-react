import { useSelector } from "react-redux";
import { RootState } from "@/redux/store";

const useAuth = () => {
    const { user, token, loading, error } = useSelector((state: RootState) => state.auth);

    return { user, token, loading, error }
};

export default useAuth;