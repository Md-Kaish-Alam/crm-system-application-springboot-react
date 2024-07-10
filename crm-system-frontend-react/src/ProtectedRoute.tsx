// src/components/ProtectedRoute.tsx
import React from "react";
import { useSelector } from "react-redux";
import { Navigate, Outlet } from "react-router-dom";
import { RootState } from "@/redux/store";

const ProtectedRoute: React.FC = () => {
  const token = useSelector((state: RootState) => state.auth.token);

  return token ? <Outlet /> : <Navigate to="/signin" />;
};

export default ProtectedRoute;
