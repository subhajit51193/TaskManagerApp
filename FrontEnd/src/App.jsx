import { useState } from "react";
import "./App.css";
import RegistrationForm from "./RegistrationForm/RegistrationForm";
import LoginForm from "./Authentication/LoginForm";
import { BrowserRouter as Router, Route, Routes,useNavigate } from "react-router-dom";
import Home from "./Home/Home";
import Navbar from "./Navbar/Navbar";
import TaskDetails from "./TaskDetails/TaskDetails";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userName, setUserName] = useState("");
  const [accessToken, setAccessToken] = useState(null);

  return (
    <div>
      {/* Other components or content can go here */}
      <Navbar isAuthenticated={isAuthenticated} />
      <Routes>
        <Route path="/login" element={<LoginForm setIsAuthenticated={setIsAuthenticated} setUserName={setUserName} setAccessToken={setAccessToken}/>} />
        <Route path="/registration" element={<RegistrationForm setIsAuthenticated={setIsAuthenticated} setUserName={setUserName} setAccessToken={setAccessToken}/>} />
        <Route path="/" element={<Home isAuthenticated={isAuthenticated} userName={userName} accessToken={accessToken}/> } />
        <Route path="/task-details/:taskId" element={<TaskDetails />} />
      </Routes>
    </div>
  );
}

export default App;
