

// src/pages/EmployeeDashboard.js (aka Layout)
import React from 'react';
import { Outlet } from 'react-router-dom'; //placeholder to render child routes
import AdminNavbar from '../components/Admin/AdminNavbar';
import Sidebar from '../components/Admin/AdminSidebar';
import '../styles/EmployeeDashboard.css';

const AdminDashboardLayout = () => {
  return (
    <div className="employee-dashboard">
      <AdminNavbar />

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

export default AdminDashboardLayout;
