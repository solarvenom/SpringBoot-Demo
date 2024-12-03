import React, { useState, useEffect } from 'react';
import { ApiData, Tab } from '../interfaces'
import { TableColumn } from '../types'
import { TableComponent } from './TableComponent';
import SearchBar from './SearchBar';

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
    { header: "SKU", accessor: "sku"},
    { header: "Created Date", accessor: "createdDate"},
    { header: "Status", accessor: "status"}
];

const TabsPanel: React.FC = () => {
    const [activeTab, setActiveTab] = useState<number>(0);
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [data, setData] = useState<ApiData[] | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
  
    useEffect(() => {
        fetchData();
    }, [activeTab]);

    useEffect(()=>{
        fetchData();
    }, [searchTerm])

    const tabs: Tab[] = [
        { id: 0, label: 'Products', apiEndpoint: `http://localhost:8080/products?searchTerm=${searchTerm}` },
        { id: 1, label: 'Variants', apiEndpoint: `http://localhost:8080/product-variants?searchTerm=${searchTerm}` },
        { id: 2, label: 'Orders', apiEndpoint: `http://localhost:8080/orders?searchTerm=${searchTerm}` },
    ];

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
                    createdDate: formatDate(order.createdDate),
                    sku: order.productVariant.sku,
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
                const updatedData = await fetch(refreshEndpoint, { method: "GET"});
                const data = await updatedData.json()
                setTableData(activeTab, data);
            } catch (error) {
                console.error("Error deleting entity:", error);
                alert(`Error deleting entity with UUID ${uuid}.`);
            }
        }
    };

    function formatDate(dateString: string) {
        const date = new Date(dateString);
      
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
      
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');
      
        return `${day}-${month}-${year} ${hours}:${minutes}:${seconds}`;
    }
  
    return (
        <div className='content'>
            <div className='tabPanel'>
                <div className="tabButtons">
                    {tabs.map(tab => (
                        <button
                            key={tab.id}
                            onClick={() => setActiveTab(tab.id)}
                            style={{
                                padding: '10px 20px',
                                marginRight: '10px',
                                cursor: 'pointer',
                                backgroundColor: activeTab === tab.id ? '#ff5555' : '#8be9fd',
                                color: activeTab === tab.id ? '#fff' : '#282c34',
                                border: activeTab === tab.id ? '1px solid #ff5555' : '1px solid #8be9fd',
                                borderRadius: '4px',
                            }}>
                                {tab.label}
                        </button>
                    ))}
                </div>
                { activeTab === 0 ? (
                    <SearchBar 
                        onSearch={(searchTerm)=>{setSearchTerm(searchTerm)}}
                        placeholderText='Search by product name or description...'
                    />
                ) : activeTab === 1 ? (
                    <SearchBar 
                        onSearch={(searchTerm)=>{setSearchTerm(searchTerm)}}
                        placeholderText='Search by product variant attributes...'
                    />
                ) : activeTab === 2 ? (
                    <SearchBar 
                        onSearch={(searchTerm)=>{setSearchTerm(searchTerm)}}
                        placeholderText='Search by order attributes...'
                    />
                ) : null }
            </div>
    
            <div className='dataPanel'>
                <div className='tableWrapper'>
                    {
                        loading ? (
                            <span className="loading">Loading...</span>
                        ) : null
                    }
                    {
                        activeTab === 0 ? (
                            <TableComponent 
                                columns={ProductColumns} 
                                data={data} 
                                deletionHandler={generateDeletionHandler(activeTab, tabs[activeTab].apiEndpoint)} 
                            />
                        ) : activeTab === 1 ? (
                            <TableComponent 
                                columns={ProductVariantColumns} 
                                data={data} 
                                deletionHandler={generateDeletionHandler(activeTab, tabs[activeTab].apiEndpoint)} 
                            />
                        ) : activeTab === 2 ? (
                            <TableComponent 
                                columns={OrdersColumns} 
                                data={data} 
                                deletionHandler={generateDeletionHandler(activeTab, tabs[activeTab].apiEndpoint)} 
                            />
                        ) : null
                    }
                </div>
            </div>
        </div>
    );
};
  
export default TabsPanel;