import React, { useState, useEffect } from 'react';
import { ApiData, Tab } from '../interfaces'
import { TableColumn } from '../types'
import { TableComponent } from './TableComponent';

const ProductColumns: TableColumn[] = [
    { header: "Product Name", accessor: "name" },
    { header: "Description", accessor: "description"}
];

const ProductVariantColumns: TableColumn[] = [
    { header: "Product Name", accessor: "name" },
    { header: "Colour", accessor: "colour"},
    { header: "Size", accessor: "size"},
    { header: "Price", accessor: "price"},
    { header: "Stock", accessor: "stock"},
    { header: "SKU", accessor: "sku"},
    { header: "EAN", accessor: "ean"}
];

const OrdersColumns: TableColumn[] = [
    { header: "Product Name", accessor: "name" },
    { header: "Mapping", accessor: "mapping" },
    { header: "Created Date", accessor: "createdDate"},
    { header: "Status", accessor: "status"}
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

    const setTableData = (activeTab: number, data: any) => {
        if(activeTab == 1) {
            setData(data.map((variant: any) => {
                return {
                    ...variant, 
                    name: variant.product.name,
                    endpoint: "http://localhost:8080/product-variants"
                }
            }))
        } else if (activeTab == 2) {
            setData(data.map((order: any) => {
                return {
                    ...order,
                    name: order.productVariant.product.name,
                    status: order.deletedDate ? "Canceled" : "Active",
                    endpoint: "http://localhost:8080/orders"
                }
            }))
        } else {
            setData(data.map((product: any) => {
                return {
                    ...product,
                    endpoint: "http://localhost:8080/products"
                }
            }));
        }
    }

    const generateDeletionHandler = (activeTab: number, refreshEndpoint: string) => {
        return async (deletinEndpoint: string, uuid: string) => {
            try {
                const response = await fetch(`${deletinEndpoint}/${uuid}`, {
                    method: "DELETE"
                });
                
                if (!response.ok) {
                    alert(`Failed to delete entity with UUID ${uuid}.`);
                }
                const updatedData = await fetch(refreshEndpoint);
                setTableData(activeTab, updatedData);
            } catch (error) {
                console.error("Error deleting entity:", error);
                alert(`Error deleting entity with UUID ${uuid}.`);
            }
        }
    };
  
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

                    setTableData(activeTab, result);
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
                    activeTab === 0 ? (<TableComponent columns={ProductColumns} data={data} deletionHandler={generateDeletionHandler(activeTab, tabs[activeTab].apiEndpoint)} />) : 
                    activeTab === 1 ? (<TableComponent columns={ProductVariantColumns} data={data} deletionHandler={generateDeletionHandler(activeTab, tabs[activeTab].apiEndpoint)} />) : 
                    activeTab === 2 ? (<TableComponent columns={OrdersColumns} data={data} deletionHandler={generateDeletionHandler(activeTab, tabs[activeTab].apiEndpoint)} />) : null
                }
            </div>
        </div>
    );
};
  
export default TabsPanel;