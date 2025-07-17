

// src/pages/EmployeeDashboard.js (aka Layout)
import React from 'react';
import { Outlet } from 'react-router-dom';
import EmployeeNavbar from '../components/Employee/EmployeeNavbar';
import Sidebar from '../components/Employee/Sidebar';
import '../styles/EmployeeDashboard.css';

const EmployeeDashboardLayout = () => {
  return (
    <div className="employee-dashboard">
      <EmployeeNavbar />

      <div className="dashboard-body">
        <Sidebar />

        {/* Content will load here */}
        <div className="dashboard-main">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default EmployeeDashboardLayout;
