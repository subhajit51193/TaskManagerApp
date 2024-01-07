// RegistrationForm.js
import React, { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from 'react-router-dom';

const RegistrationForm = ({ setIsAuthenticated, setUserName,setAccessToken }) => {

  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  // Add other necessary state variables

  const handleRegistration = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/register", {
        email,
        password,
        firstName,
        lastName,
        // Add other registration data
      });

      const accessToken = response.data.token;
      console.log("Token"+accessToken);

      setAccessToken(accessToken);
      
      setIsAuthenticated(true);
      setUserName(response.data.firstName); 
      navigate('/')
      console.log("API Response:", response);
      console.log("Registration successful:", response.data);
      // Handle storing the token and other user data in state or context
    } catch (error) {
      console.error("Registration failed:", error.response.data);
      // Handle error, e.g., show error message to the user
    }
  };

  return (
    <div className="max-w-md mx-auto my-8 p-6 bg-white rounded-md shadow-md">
      <h2 className="text-2xl font-bold mb-4">Registration Form</h2>
      <input
        type="text"
        placeholder="Email"
        className="w-full py-2 px-4 mb-4 border border-gray-300 rounded-md"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        className="w-full py-2 px-4 mb-4 border border-gray-300 rounded-md"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <input
        type="text"
        placeholder="First Name"
        className="w-full py-2 px-4 mb-4 border border-gray-300 rounded-md"
        value={firstName}
        onChange={(e) => setFirstName(e.target.value)}
      />
      <input
        type="text"
        placeholder="Last Name"
        className="w-full py-2 px-4 mb-4 border border-gray-300 rounded-md"
        value={lastName}
        onChange={(e) => setLastName(e.target.value)}
      />
      {/* Add other input fields for registration data */}
      <button
        className="w-full py-2 px-4 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:shadow-outline-blue"
        onClick={handleRegistration}
      >
        Register
      </button>
      <div className="mt-4">
        <p className="text-sm text-gray-600">
          Already have an account?{' '}
          <Link to="/login" className="text-blue-500 hover:underline">
            Login
          </Link>
        </p>
      </div>
    </div>
  );
};

export default RegistrationForm;
