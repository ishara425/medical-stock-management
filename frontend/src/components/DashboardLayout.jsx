import React, { useState } from 'react';
import { Routes, Route, useNavigate, useLocation, Navigate } from 'react-router-dom';
import { LogOut } from 'lucide-react';
import Sidebar from './Sidebar';
import MedicineDashboard from './MedicineDashboard';
import StockDashboard from './StockDashboard';
import MedicineDistributions from './MedicineDistributions';

const DashboardLayout = () => {
  const navigate = useNavigate();
  const location = useLocation();
  
  // Determine active tab from current route
  const getActiveTab = () => {
    const path = location.pathname;
    if (path.includes('/medicines')) return 'medicines';
    if (path.includes('/stock')) return 'stock';
    if (path.includes('/distributions')) return 'distributions';
    if (path.includes('/reports')) return 'reports';
    return 'medicines';
  };

  const [activeTab, setActiveTab] = useState(getActiveTab());

  const handleTabChange = (tabId) => {
    setActiveTab(tabId);
    
    const routes = {
      medicines: '/dashboard/medicines',
      stock: '/dashboard/stock',
      distributions: '/dashboard/distributions',
      reports: '/dashboard/reports'
    };
    
    navigate(routes[tabId]);
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <div className="flex min-h-screen">
      <Sidebar activeTab={activeTab} setActiveTab={handleTabChange} />
      
      <div className="flex-1 ml-64">
        {/* Shared Header */}
        <header className="bg-white border-b px-8 py-4 flex justify-between items-center">
          <div>
            <h1 className="text-2xl font-semibold text-gray-800">Medicine Tracker</h1>
            <p className="text-sm text-gray-500">Public Health Inspector Dashboard</p>
          </div>
          <div className="flex items-center gap-4">
            <span className="text-sm text-gray-600">Welcome, PHI ishara</span>
            <button 
              onClick={handleLogout}
              className="flex items-center gap-2 text-red-600 hover:text-red-700"
            >
              <LogOut className="w-4 h-4" />
              Logout
            </button>
          </div>
        </header>

        {/* Route Content */}
        <Routes>
          <Route path="/" element={<Navigate to="/dashboard/medicines" replace />} />
          <Route path="/medicines" element={<MedicineDashboard />} />
          <Route path="/stock" element={<StockDashboard />} /> 
          <Route path="/distributions" element={<MedicineDistributions />} /> 
          <Route path="/reports" element={
            <div className="p-8">
              <h2 className="text-2xl font-bold">Reports</h2>
              <p className="text-gray-600">Coming soon...</p>
            </div>
          } />
        </Routes>
      </div>
    </div>
  );
};

export default DashboardLayout;