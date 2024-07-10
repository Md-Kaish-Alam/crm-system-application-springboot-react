import axiosInstance from "@/axiosInstance";
import { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "@/redux/store";
import { User } from "@/types/types";
import { clearAuthState } from "@/features/auth/authSlice";

const Dashboard = () => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  const token = useSelector((state: RootState) => state.auth.token);
  const dispatch = useDispatch();

  console.log({ token });

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const response = await axiosInstance.get<User>("/user/details", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setUser(response.data);
      } catch (error) {
        console.error("Error fetching user details:", error);
        dispatch(clearAuthState());
      } finally {
        setLoading(false);
      }
    };

    if (token) {
      fetchUserDetails();
    } else {
      setLoading(false);
    }
  }, [token, dispatch]);

  if (loading) {
    return <p>Loading user details...</p>;
  }

  return (
    <div className="h-screen flex items-center justify-center">
      <div className="border w-[30vw] shadow-md p-10">
        <div className="flex flex-col items-center justify-center gap-4">
          <h1 className="text-3xl font-bold">Welcome to Dashboard</h1>
          {user ? (
            <>
              <p>Hello {user.fullName}!</p>
              <p>Email: {user.email}</p>
              <p>Role: {user.role}</p>
            </>
          ) : (
            <p>Error fetching user details. Please try again later.</p>
          )}
          <button
            onClick={() => {
              localStorage.removeItem("token");
              dispatch(clearAuthState());
            }}
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          >
            Logout
          </button>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
