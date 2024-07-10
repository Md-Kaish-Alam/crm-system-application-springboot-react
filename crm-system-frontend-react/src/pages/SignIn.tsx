import { useDispatch, useSelector } from "react-redux";
import { ChangeEvent, FormEvent, useEffect, useState } from "react";

import { User } from "@/types/types";
import { AppDispatch, RootState } from "@/redux/store";
import { signin } from "@/features/auth/authSlice";
import { useNavigate } from "react-router-dom";

const SignIn = () => {
  const [user, setUser] = useState<User>({ email: "", password: "" });

  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();
  const { token } = useSelector((state: RootState) => state.auth);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    dispatch(signin(user));
  };

  useEffect(() => {
    if (token) {
      navigate("/dashboard");
    }
  });

  return (
    <div className="h-screen flex items-center justify-center">
      <form onSubmit={handleSubmit} className="border w-[30vw] shadow-md p-10">
        <div className="flex flex-col items-center justify-center gap-8">
          <h1 className="text-xl font-bold">Login Page</h1>
          <div className="w-full flex flex-col items-center justify-center gap-4">
            <input
              type="text"
              name="email"
              value={user.email}
              placeholder="Email address"
              className="border p-2 w-full rounded-md"
              onChange={handleChange}
            />
            <input
              type="password"
              name="password"
              value={user.password}
              placeholder="Password"
              className="border p-2 w-full rounded-md"
              onChange={handleChange}
            />
            <button
              className="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              type="submit"
            >
              Login
            </button>
          </div>
          <p className="text-sm">
            Not a member ?{" "}
            <a
              href="/signup"
              className="text-blue-500 underline underline-offset-2"
            >
              Register
            </a>
          </p>
        </div>
      </form>
    </div>
  );
};

export default SignIn;
