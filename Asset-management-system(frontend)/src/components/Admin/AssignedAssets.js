import React, { useState, useEffect } from 'react';
import '../../styles/AssignedAssets.css';
import { FaUserFriends, FaLaptop, FaCheckCircle } from 'react-icons/fa';
import API from '../../services/api'; // axios instance with token Pre-configured Axios instance for calling the backend.

const AssignedAssets = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [sortType, setSortType] = useState('name');
  const [employees, setEmployees] = useState([]);

  useEffect(() => {
  const fetchAssignedAssets = async () => {
    try {
      const res = await API.get('/admin/assigned-assets');
      const data = res.data;

      if (Array.isArray(data)) {
        setEmployees(data);
      } else if (Array.isArray(data.employees)) {
        setEmployees(data.employees);
      } else {
        console.error('Unexpected API response format:', data);
        setEmployees([]);
      }
    } catch (err) {
      console.error('Failed to fetch assigned assets 💥', err);
      setEmployees([]);
    }
  };

  fetchAssignedAssets();
}, []);



  const totalEmployees = employees.length;
  const assetsAllocated = employees.filter(e => e.assetNames.length > 0).length;
  const activeNow = employees.filter(e => e.active).length;

  const filteredEmployees = employees
    .filter(emp =>
      emp.employeeName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      emp.department.toLowerCase().includes(searchTerm.toLowerCase()) ||
      emp.phoneNumber.includes(searchTerm)
    )
    .sort((a, b) => {
      if (sortType === 'name') return a.employeeName.localeCompare(b.employeeName);
      if (sortType === 'status') return b.active - a.active;
      return 0;
    });

  return (
    <div className="assigned-assets">
      <h2>Assigned Assets</h2>

      {/* Top Stats */}
      <div className="stats-grid">
        <div className="stat-card">
          <div className="icon-wrap"><FaUserFriends className="icon" /></div>
          <div>
            <h3>{totalEmployees}</h3>
            <p>Total Employees</p>
          </div>
        </div>

        <div className="stat-card">
          <div className="icon-wrap"><FaLaptop className="icon" /></div>
          <div>
            <h3>{assetsAllocated}</h3>
            <p>Assets Allocated</p>
          </div>
        </div>

        <div className="stat-card">
          <div className="icon-wrap"><FaCheckCircle className="icon" /></div>
          <div>
            <h3>{activeNow}</h3>
            <p>Active Now</p>
          </div>
        </div>
      </div>

      {/* Table */}
      <div className="employee-table">
        <div className="table-controls">
          <input
            type="text"
            placeholder="Search by name, dept or phone"
            className="search-input"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <select
            className="sort-dropdown"
            value={sortType}
            onChange={(e) => setSortType(e.target.value)}
          >
            <option value="name">Sort by: Name (A-Z)</option>
            <option value="status">Sort by: Status (Active First)</option>
          </select>
        </div>

        <table className="emp-table">
          <thead>
            <tr>
              <th>Employee Name</th>
              <th>Department</th>
              <th>Phone Number</th>
              <th>Asset Allocated</th>
              <th>Email</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {filteredEmployees.map((emp, i) => (
              <tr key={i}>
                <td>{emp.employeeName}</td>
                <td>{emp.department}</td>
                <td>{emp.phoneNumber}</td>
                <td>{emp.assetNames?.join(' and ') || 'None'}</td>
                <td>{emp.email}</td>
                <td>
                  <span className={`status ${emp.active ? 'active' : 'inactive'}`}>
                    {emp.active ? 'Active' : 'Inactive'}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AssignedAssets;


