import React, { useState, useEffect } from 'react';
import { FiEdit2, FiTrash2, FiPlus, FiX } from 'react-icons/fi';
import '../../styles/ManageAssets.css';
import API from '../../services/api';

const ManageAssets = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('All Categories');
  const [selectedStatus, setSelectedStatus] = useState('All Status');
  const [assets, setAssets] = useState([]);
  const [categoryList, setCategoryList] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [isEdit, setIsEdit] = useState(false);
  const [alert, setAlert] = useState({ type: '', message: '' });

  const [current, setCurrent] = useState({
    id: null,
    name: '',
    assetNumber: '',
    categoryName: '',
    status: 'AVAILABLE',
    assetCondition: 'GOOD'
  });

  const statusOptions = ['All Status', 'AVAILABLE', 'ASSIGNED', 'RETIRED', 'UNDER_REPAIR'];

  useEffect(() => {
    fetchAssets();
    fetchCategories();
  }, []);

  const fetchAssets = async () => {
  try {
    const res = await API.get('/assets');
    const data = res.data;

    if (Array.isArray(data)) {
      setAssets(data);
    } else if (Array.isArray(data.assets)) {
      setAssets(data.assets);
    } else {
      console.error('Unexpected assets response:', data);
      setAssets([]);
    }
  } catch (err) {
    console.error('Error fetching assets:', err);
    setAssets([]);
  }
};



  const fetchCategories = async () => {
    try {
      const res = await API.get('/categories');
      setCategoryList(res.data);
    } catch (err) {
      console.error('Error fetching categories:', err);
    }
  };

  /*const filtered = assets.filter(a => {
    const matchesSearch =
      a.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      a.assetNumber.toLowerCase().includes(searchTerm.toLowerCase()) ||
      a.categoryName.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesCategory = selectedCategory === 'All Categories' || a.categoryName === selectedCategory;
    const matchesStatus = selectedStatus === 'All Status' || a.status === selectedStatus;

    return matchesSearch && matchesCategory && matchesStatus;
  });*/
  const filtered = Array.isArray(assets)
  ? assets.filter(a => {
      const matchesSearch =
        a.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        a.assetNumber.toLowerCase().includes(searchTerm.toLowerCase()) ||
        a.categoryName.toLowerCase().includes(searchTerm.toLowerCase());

      const matchesCategory =
        selectedCategory === 'All Categories' || a.categoryName === selectedCategory;

      const matchesStatus =
        selectedStatus === 'All Status' || a.status === selectedStatus;

      return matchesSearch && matchesCategory && matchesStatus;
    })
  : [];


  const resetForm = () => {
    setCurrent({
      id: null,
      name: '',
      assetNumber: '',
      categoryName: '',
      status: 'AVAILABLE',
      assetCondition: 'GOOD'
    });
    setIsEdit(false);
  };

  const openAdd = () => {
    resetForm();
    setModalOpen(true);
  };

  const openEdit = (a) => {
    setCurrent({ ...a });
    setIsEdit(true);
    setModalOpen(true);
  };

  const deleteAsset = async (id) => {
    const confirmDelete = window.confirm("Are you sure you want to delete this asset?");
    if (!confirmDelete) return;
    try {
      await API.delete(`/assets/${id}`);
      setAssets(prev => prev.filter(a => a.id !== id));
      setAlert({ type: 'success', message: 'Asset deleted successfully.' });
    } catch (err) {
      console.error(err);
      setAlert({ type: 'error', message: 'Failed to delete asset.' });
    }
  };

  const handleSave = async () => {
    if (!current.name || !current.assetNumber || !current.categoryName) {
      setAlert({ type: 'error', message: 'Name, number, and category are required.' });
      return;
    }

    try {
      if (isEdit) {
        await API.put(`/assets/${current.id}`, current);
        setAlert({ type: 'success', message: 'Asset updated!' });
      } else {
        const res = await API.post('/assets', current);
        setAssets(prev => [...prev, res.data]);
        setAlert({ type: 'success', message: 'Asset added!' });
      }
      setModalOpen(false);
      fetchAssets();
      resetForm();
    } catch (err) {
      console.error(err);
      setAlert({ type: 'error', message: 'Error saving asset.' });
    }
  };

  return (
    <div className="manage-assets">
      {alert.message && (
        <div className={`alert-box ${alert.type}`}>
          <span>{alert.message}</span>
          <button onClick={() => setAlert({ type: '', message: '' })}><FiX /></button>
        </div>
      )}

      <div className="header-row">
        <h2>Manage <span>Assets</span></h2>
        <button className="add-btn" onClick={openAdd}><FiPlus /> Add Asset</button>
      </div>

      <div className="filters">
        <input
          className="search-bar"
          type="text"
          placeholder="Search by name, number, category..."
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
        />
        <select
          className="dropdown"
          value={selectedCategory}
          onChange={e => setSelectedCategory(e.target.value)}
        >
          <option value="All Categories">All Categories</option>
          {categoryList.map(cat => (
            <option key={cat.id} value={cat.name}>{cat.name}</option>
          ))}
        </select>
        <select
          className="dropdown"
          value={selectedStatus}
          onChange={e => setSelectedStatus(e.target.value)}
        >
          {statusOptions.map((status, idx) => (
            <option key={idx} value={status}>{status}</option>
          ))}
        </select>
      </div>

      <table className="asset-table">
        <thead>
          <tr>
            <th>Asset No</th>
            <th>Name</th>
            <th>Category</th>
            <th>Status</th>
            <th>Condition</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filtered.map(a => (
            <tr key={a.id}>
              <td>{a.assetNumber}</td>
              <td>{a.name}</td>
              <td>{a.categoryName}</td>
              <td>{a.status}</td>
              <td>{a.assetCondition}</td>
              <td>
                <button onClick={() => openEdit(a)}><FiEdit2 /></button>
                <button onClick={() => deleteAsset(a.id)}><FiTrash2 /></button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {modalOpen && (
        <div className="modal-overlay">
          <div className="modal-box">
            <div className="modal-header">
              <h3>{isEdit ? 'Edit Asset' : 'Add Asset'}</h3>
              <button onClick={() => { setModalOpen(false); resetForm(); }}><FiX /></button>
            </div>
            <input
              placeholder="Name"
              value={current.name}
              onChange={e => setCurrent({ ...current, name: e.target.value })}
    
            />
            <input
              placeholder="Asset Number"
              value={current.assetNumber}
              onChange={e => setCurrent({ ...current, assetNumber: e.target.value })}
            />
            <select
              value={current.categoryName}
              onChange={e => setCurrent({ ...current, categoryName: e.target.value })}
            >
              <option value="" disabled>-- Select Category --</option>
              {categoryList.map(cat => (
                <option key={cat.id} value={cat.name}>{cat.name}</option>
              ))}
            </select>
            <select
              value={current.status}
              onChange={e => setCurrent({ ...current, status: e.target.value })}
              disabled={isEdit}
            >
              <option>AVAILABLE</option>
              <option>ASSIGNED</option>
              <option>RETIRED</option>
              <option>UNDER_REPAIR</option>
            </select>
            <select
              value={current.assetCondition}
              onChange={e => setCurrent({ ...current, assetCondition: e.target.value })}
            >
              <option>GOOD</option>
              <option>DAMAGED</option>
              <option>REPAIR</option>
            </select>
            <div className="modal-actions">
              <button className="submit" onClick={handleSave}>
                {isEdit ? 'Update' : 'Submit'}
              </button>
              <button className="cancel" onClick={() => { setModalOpen(false); resetForm(); }}>
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ManageAssets;
