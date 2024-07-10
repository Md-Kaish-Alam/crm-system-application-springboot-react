import { ChangeEvent, FormEvent, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import { User } from "@/types/types";
import { AppDispatch, RootState } from "@/redux/store";
import { signup } from "@/features/auth/authSlice";

const SignUp = () => {
  const [user, setUser] = useState<User>({
    email: "",
    password: "",
    fullName: "",
    mobile: "",
    role: "USER",
  });

  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();
  const { token } = useSelector((state: RootState) => state.auth);

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    dispatch(signup(user));
  };

  useEffect(() => {
    if (token) {
      navigate("/signin");
    }
  }, [token, navigate]);

  return (
    <div className="h-screen flex items-center justify-center">
      <form onSubmit={handleSubmit} className="border w-[30vw] shadow-md p-10">
        <div className="flex flex-col items-center justify-center gap-8">
          <h1 className="text-xl font-bold">Sign Up Page</h1>
          <div className="w-full flex flex-col items-center justify-center gap-4">
            <input
              name="fullName"
              type="text"
              value={user.fullName}
              placeholder="Full Name"
              className="border p-2 w-full rounded-md"
              onChange={handleChange}
            />
            <input
              name="email"
              type="email"
              value={user.email}
              placeholder="Email address"
              className="border p-2 w-full rounded-md"
              onChange={handleChange}
            />
            <input
              name="password"
              type="password"
              value={user.password}
              placeholder="Password"
              className="border p-2 w-full rounded-md"
              onChange={handleChange}
            />
            <input
              name="mobile"
              type="text"
              value={user.mobile}
              placeholder="Mobile Number"
              className="border p-2 w-full rounded-md"
              onChange={handleChange}
            />
            <select
              name="role"
              value={user.role}
              onChange={handleChange}
              className="border p-2 w-full rounded-md"
            >
              <option value="USER">USER</option>
              <option value="ADMIN">ADMIN</option>
            </select>
            <button
              className="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              type="submit"
            >
              Sign Up
            </button>
          </div>
          <p className="text-sm">
            Already register ?{" "}
            <a
              href="/signin"
              className="text-blue-500 underline underline-offset-2"
            >
              Login
            </a>
          </p>
        </div>
      </form>
    </div>
  );
};

export default SignUp;
