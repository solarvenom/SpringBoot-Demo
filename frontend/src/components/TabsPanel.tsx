import React, { useState, useEffect } from 'react';
import { ApiData, Tab } from '../interfaces'
import { TableColumn } from '../types'
import { TableComponent } from './TableComponent';

const ProductColumns: TableColumn[] = [
    { header: "Name", accessor: "name" },
    { header: "Description", accessor: "description"},
    { header: "Options", accessor: "options"}
];

const ProductVariantColumns: TableColumn[] = [
    { header: "Product Name", accessor: 'product["name"]' },
    { header: "Colour", accessor: "colour"},
    { header: "Size", accessor: "size"},
    { header: "Price", accessor: "price"},
    { header: "Stock", accessor: "stock"},
    { header: "SKU", accessor: "sku"},
    { header: "EAN", accessor: "ean"},
    { header: "Options", accessor: "options"}
];

const OrdersColumns: TableColumn[] = [
    { header: "Product Name", accessor: 'product["name"]' },
    { header: "Colour", accessor: "colour"},
    { header: "Size", accessor: "size"},
    { header: "Price", accessor: "price"},
    { header: "Stock", accessor: "stock"},
    { header: "SKU", accessor: "sku"},
    { header: "EAN", accessor: "ean"},
    { header: "Options", accessor: "options"}
];


const TabsPanel: React.FC = () => {
    const [activeTab, setActiveTab] = useState<number>(0);
    const [data, setData] = useState<ApiData[] | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
  
    const tabs: Tab[] = [
      { id: 0, label: 'Products', apiEndpoint: 'http://localhost:8080/products' },
      { id: 1, label: 'Variants', apiEndpoint: 'http://localhost:8080/product-variants?searchTerm=' },
      { id: 2, label: 'Orders', apiEndpoint: 'http://localhost:8080/orders' },
    ];
  
    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);
    
            try {
                const activeTabDetails = tabs.find(tab => tab.id === activeTab);
                if (activeTabDetails) {
                    const response = await fetch(activeTabDetails.apiEndpoint);
                    if (!response.ok) throw new Error(`Error ${response.status}: ${response.statusText}`);
                    const result = await response.json();
                    setData(result);
                }
            } catch (err) {
                setError(err instanceof Error ? err.message : 'An unknown error occurred');
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [activeTab]);
  
    return (
        <div className='content' style={{ display: 'flex', padding: '20px', border: '1px solid #ccc', borderRadius: '8px' }}>
            <div className='tabPanel' style={{ display: 'flex', marginBottom: '20px' }}>
                {tabs.map(tab => (
                    <button 
                        key={tab.id}
                        onClick={() => setActiveTab(tab.id)}
                        style={{
                            padding: '10px 20px',
                            marginRight: '10px',
                            cursor: 'pointer',
                            backgroundColor: activeTab === tab.id ? '#007BFF' : '#f8f9fa',
                            color: activeTab === tab.id ? '#fff' : '#000',
                            border: '1px solid #007BFF',
                            borderRadius: '4px',
                        }}>
                        {tab.label}
                    </button>
                ))}
            </div>
    
            <div className='dataPanel'>
                {
                    activeTab === 0 ? (<TableComponent columns={ProductColumns} data={data} />) : 
                    activeTab === 1 ? (<TableComponent columns={ProductVariantColumns} data={data} />) : 
                    activeTab === 2 ? (<TableComponent columns={OrdersColumns} data={data} />) : null
                }
            </div>
        </div>
    );
};
  
export default TabsPanel;
  