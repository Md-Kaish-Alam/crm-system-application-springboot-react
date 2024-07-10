import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";

import SignIn from "@/pages/SignIn";
import SignUp from "@/pages/SignUp";
import Dashboard from "@/pages/Dashboard";
import ProtectedRoute from "@/ProtectedRoute";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/signin" element={<SignIn />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/" element={<ProtectedRoute />}>
          <Route path="dashboard" element={<Dashboard />} />
        </Route>
        <Route path="*" element={<Navigate to="/signin" />} />
      </Routes>
    </Router>
  );
};

export default App;
