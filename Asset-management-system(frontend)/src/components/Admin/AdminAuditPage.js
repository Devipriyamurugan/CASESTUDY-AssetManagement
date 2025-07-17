import React, { useState, useEffect } from "react";
import {
  Box,
  Button,
  Container,
  MenuItem,
  Select,
  TextField,
  Typography,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Paper,
} from "@mui/material";
import API from "../../services/api";
import "../../styles/AdminAuditPage.css";

const AdminAuditPage = () => {
  const [employees, setEmployees] = useState([]);
  const [employeeAssets, setEmployeeAssets] = useState([]);
  const [auditLogs, setAuditLogs] = useState([]);

  const [formData, setFormData] = useState({
    employeeId: "",
    assetId: "",
    description: "",
  });

  useEffect(() => {
    fetchEmployees();
    fetchAuditLogs();
  }, []);

  const fetchEmployees = async () => {
  try {
    const res = await API.get("/employees");
    console.log("Employees response:", res.data); // Debug log
    setEmployees(Array.isArray(res.data) ? res.data : []); // Defensive
  } catch (err) {
    console.error("Failed to fetch employees", err);
    setEmployees([]); // fallback to empty array
  }
};


  const fetchAuditLogs = async () => {
    try {
      const res = await API.get("/audit/logs");
      const sortedLogs = [...res.data].sort(
        (a, b) => new Date(b.auditDate) - new Date(a.auditDate)
      );
      setAuditLogs(sortedLogs);
    } catch (err) {
      console.error("Failed to fetch audit logs", err);
    }
  };

  const fetchAssetsForEmployee = async (empId) => {
  try {
    const res = await API.get(`/assets/assigned/${empId}`);
    console.log("Assets response:", res.data); // Debug output

    setEmployeeAssets(Array.isArray(res.data) ? res.data : []);
  } catch (err) {
    console.error("Failed to fetch assets for employee", err);
    setEmployeeAssets([]);
  }
};


  const handleChange = async (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
      ...(name === "employeeId" && { assetId: "" }),
    }));

    if (name === "employeeId") {
      await fetchAssetsForEmployee(value);
    }
  };

  const selectedEmployee = Array.isArray(employees)
  ? employees.find((emp) => String(emp.id) === formData.employeeId)
  : null;


  const handleSubmit = async (e) => {
    e.preventDefault();

    const employee = selectedEmployee;
    const asset = employeeAssets.find(
      (a) => String(a.id) === formData.assetId
    );

    if (!employee || !asset || !formData.description.trim()) {
      alert("Please fill all fields correctly.");
      return;
    }

    const payload = {
      performedBy: "Admin",
      status: "PENDING",
      auditDescrption: formData.description.trim(),
      adminNote: formData.description.trim(),
      employeeId: employee.id,
      assetId: asset.id,
    };

    try {
      await API.post("/audit/request", payload);
      fetchAuditLogs();
      setFormData({ employeeId: "", assetId: "", description: "" });
      setEmployeeAssets([]);
    } catch (err) {
      console.error("Failed to submit audit", err);
      alert("Failed to submit audit request. Check backend logs.");
    }
  };

  return (
    <Container maxWidth="md" className="audit-page">
      <Typography variant="h5" className="title">
        📤 Send Audit Request
      </Typography>

      <Paper elevation={3} className="form-container">
        <form onSubmit={handleSubmit}>
          <Box display="flex" flexDirection="column" gap={2}>
            <TextField
              label="Employee ID"
              name="employeeId"
              value={formData.employeeId}
              onChange={handleChange}
              select
              required
            >
              <MenuItem value="">-- Select Employee --</MenuItem>
              {employees.map((emp) => (
                <MenuItem key={emp.id} value={String(emp.id)}>
                  {emp.id} - {emp.name}
                </MenuItem>
              ))}
            </TextField>

            <TextField
              label="Employee Name"
              value={selectedEmployee?.name || ""}
              InputProps={{ readOnly: true }}
            />

            <Select
              name="assetId"
              value={formData.assetId}
              onChange={handleChange}
              displayEmpty
              required
              disabled={!formData.employeeId}
            >
              <MenuItem value="">
                {formData.employeeId
                  ? "-- Choose Asset --"
                  : "Select Employee First"}
              </MenuItem>
              {employeeAssets.map((asset) => (
                <MenuItem key={asset.id} value={String(asset.id)}>
                  {asset.name}
                </MenuItem>
              ))}
            </Select>

            <TextField
              name="description"
              label="Audit Description"
              multiline
              rows={3}
              value={formData.description}
              onChange={handleChange}
              required
            />

            <Button type="submit" variant="contained" color="primary">
              Submit Audit Request
            </Button>
          </Box>
        </form>
      </Paper>

      <Typography variant="h5" className="title" style={{ marginTop: "2rem" }}>
        📄 Audit Logs
      </Typography>

      <Paper className="table-container">
        <Box sx={{ overflowX: "auto" }}>
         <Table>
  <TableHead>
    <TableRow>
      <TableCell>#</TableCell>
      <TableCell>Employee</TableCell>
      <TableCell>Asset</TableCell>
      <TableCell>Status</TableCell>
      <TableCell>Action</TableCell>
      <TableCell>Performed By</TableCell>
      <TableCell>Admin Note</TableCell>
      <TableCell>Employee Response</TableCell>
      <TableCell>Audit Description</TableCell>
      <TableCell>Date</TableCell>
    </TableRow>
  </TableHead>
  <TableBody>
    {auditLogs.length > 0 ? (
      auditLogs.map((log, index) => (
        <TableRow key={log.id}>
          <TableCell>{index + 1}</TableCell>
          <TableCell>{log.employeeName}</TableCell>
          <TableCell>{log.assetName}</TableCell>
          <TableCell>
            <span
              className={`status-pill ${log.status?.toLowerCase() || "pending"}`}
            >
              {log.status}
            </span>
          </TableCell>
          <TableCell>{log.action || "—"}</TableCell>
          <TableCell>{log.performedBy || "—"}</TableCell>
          <TableCell>{log.adminNote || "—"}</TableCell>
          <TableCell>{log.employeeResponse || "—"}</TableCell>
          <TableCell>{log.auditDescrption || "—"}</TableCell>
          <TableCell>
            {log.auditDate ? new Date(log.auditDate).toLocaleString() : "—"}
          </TableCell>
        </TableRow>
      ))
    ) : (
      <TableRow>
        <TableCell colSpan={10} align="center">
          No audit logs found.
        </TableCell>
      </TableRow>
    )}
  </TableBody>
</Table>
        </ Box>

      </Paper>
    </Container>
  );
};

export default AdminAuditPage;