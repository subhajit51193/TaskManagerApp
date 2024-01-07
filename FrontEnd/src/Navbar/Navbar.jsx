
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Navbar = ({ isAuthenticated, accessToken }) => {

  const navigate = useNavigate();
  
  return (
    <nav className="bg-gray-800 p-4">
      <div className="container mx-auto flex justify-between items-center">
        <div className="text-white text-lg font-bold">My App</div>
        <ul className="flex space-x-4">
          <li><Link to="/" className="text-white hover:text-gray-300">Home</Link></li>
          {!isAuthenticated && (
            <>
              <li><Link to="/login" className="text-white hover:text-gray-300">Login</Link></li>
              <li><Link to="/registration" className="text-white hover:text-gray-300">Register</Link></li>
            </>
          )}
        </ul>
        {isAuthenticated && (
          <button
            // onClick={handleLogout} 
            className="bg-red-500 text-white py-2 px-4 rounded-md hover:bg-red-600 focus:outline-none focus:shadow-outline-red">
            Logout
          </button>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
