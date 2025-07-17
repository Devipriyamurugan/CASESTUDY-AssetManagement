import React, { useEffect, useState } from "react";
import API from "../../services/api";
import "../../styles/EmployeeAuditPage.css";

const EmployeeAuditPage = () => {
  const [auditRequests, setAuditRequests] = useState([]);
  const [responses, setResponses] = useState({});
  const [submittedIds, setSubmittedIds] = useState([]);
  const [successMsg, setSuccessMsg] = useState(null);

  const fetchAuditRequests = async () => {
    const user = JSON.parse(localStorage.getItem("loggedInUser"));
    if (!user || !user.id) return;

    try {
      const res = await API.get(`/audit/user/${user.id}`);
      setAuditRequests(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      console.error("Failed to fetch audit requests", err);
    }
  };

  useEffect(() => {
    fetchAuditRequests();
  }, []);

  const handleSelectChange = (id, value) => {
    setResponses((prev) => ({
      ...prev,
      [id]: {
        ...prev[id],
        action: value,
      },
    }));
  };

  const handleResponseChange = (id, value) => {
    setResponses((prev) => ({
      ...prev,
      [id]: {
        ...prev[id],
        employeeResponse: value,
      },
    }));
  };

  const handleSubmit = async (id) => {
    const response = responses[id];
    const user = JSON.parse(localStorage.getItem("loggedInUser"));
    const audit = auditRequests.find((r) => r.id === id);

    if (!response?.action || !response.employeeResponse?.trim()) {
      alert("Please select an action and enter your response.");
      return;
    }

    const payload = {
      action: response.action,
      status: response.action.toUpperCase(), // Backend requires status too
      performedBy: user.name || "Employee",
      auditDescrption: response.employeeResponse.trim(), //  backend expects this as @NotBlank
      employeeResponse: response.employeeResponse.trim(),
      auditDate: new Date().toISOString(),
    };

    try {
      await API.put(`/audit/respond/${id}`, payload);
      setSubmittedIds((prev) => [...prev, id]);
      setSuccessMsg(`✅ Audit for "${audit.assetName}" submitted!`);
      setTimeout(() => setSuccessMsg(null), 3000);
    } catch (err) {
      console.error("Submission error:", err);
      alert("Failed to submit audit.");
    }
  };

  return (
    <div className="audit-container">
      <h2 className="audit-header">📝 Pending Audit Verifications</h2>

      {successMsg && <p className="audit-success">{successMsg}</p>}

      {auditRequests.length === 0 ? (
        <p className="no-audit-text">No audits pending for you.</p>
      ) : (
        <div className="audit-list">
          {auditRequests.map((req) => {
            const isSubmitted = submittedIds.includes(req.id);
            const response = responses[req.id] || {};

            return (
              <div key={req.id} className="audit-card">
                <div className="audit-info">
                  <strong>🖥️ Asset:</strong> {req.assetName}
                </div>
                <div className="audit-info">
                  <strong>📋 Admin Note:</strong> {req.adminNote || "—"}
                </div>

                <div className="audit-actions">
                  <div className="audit-field">
                    <label className="audit-label">✅ Your Action</label>
                    <select
                      value={response.action || ""}
                      onChange={(e) =>
                        handleSelectChange(req.id, e.target.value)
                      }
                      className="audit-select"
                      disabled={isSubmitted}
                      required
                    >
                      <option value="">-- Select Action --</option>
                      <option value="Verified">Verified</option>
                      <option value="Rejected">Rejected</option>
                    </select>
                  </div>

                  <div className="audit-field full-width">
                    <label className="audit-label">🧾 Your Response</label>
                    <input
                      type="text"
                      value={response.employeeResponse || ""}
                      onChange={(e) =>
                        handleResponseChange(req.id, e.target.value)
                      }
                      className="audit-input"
                      placeholder="e.g., Asset is in good condition"
                      disabled={isSubmitted}
                      required
                    />
                  </div>

                  {isSubmitted ? (
                    <div
                      className={`status-pill ${response.action.toLowerCase()}`}
                    >
                      {response.action}
                    </div>
                  ) : (
                    <button
                      onClick={() => handleSubmit(req.id)}
                      className="audit-submit-btn"
                    >
                      Submit
                    </button>
                  )}
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default EmployeeAuditPage;
