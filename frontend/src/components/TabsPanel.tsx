import React, { useState, useEffect } from 'react';
import { ApiData, Tab, Product, ProductVariant } from '../interfaces';
import { TableComponent } from './TableComponent';
import SearchBar from './SearchBar';
import PopUp from './PopUp';
import { URL, ProductColumns, ProductVariantColumns, OrdersColumns } from '../const';
import { RequestMethod, Entity, EntityIndex } from '../enums';
import { 
    createDataSetter, 
    generateDeletionHandler, 
    generateSubmitionHandler, 
    reduceToUniqueProducts, 
    reduceToUniqueProductVariants 
} from '../utils';

const TabsPanel: React.FC = () => {
    const [activeTab, setActiveTab] = useState<number>(0);
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [data, setData] = useState<ApiData[] | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [isPopupOpen, setIsPopupOpen] = useState<boolean>(false);
    const [products, setProducts] = useState<Product[] | null>(null)
    const [productVariants, setProductVariants] = useState<ProductVariant[] | null>(null)
  
    useEffect(() => {
        fetchData();
    }, [activeTab]);

    useEffect(()=>{
        fetchData();
    }, [searchTerm])

    const tabs: Tab[] = [
        { id: EntityIndex.PRODUCTS, label: Entity.PRODUCTS, apiEndpoint: `${URL.SEARCH_PRODUCTS}${searchTerm}` },
        { id: EntityIndex.PRODUCT_VARIANTS, label: Entity.PRODUCT_VARIANTS, apiEndpoint: `${URL.SEARCH_PRODUCT_VARIANTS}${searchTerm}` },
        { id: EntityIndex.ORDERS, label: Entity.ORDERS, apiEndpoint: `${URL.SEARCH_ORDERS}${searchTerm}` },
    ];

    const setTableData = createDataSetter(setData);
    const deletionHandler = generateDeletionHandler(setTableData);
    const submitionHandler = generateSubmitionHandler(setTableData);
    const togglePopup = () => setIsPopupOpen(!isPopupOpen);

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
                if(activeTab === EntityIndex.PRODUCTS){ setProducts(reduceToUniqueProducts(result)) }
                if(activeTab === EntityIndex.PRODUCT_VARIANTS) { setProductVariants(reduceToUniqueProductVariants(result)) }
            }
        } catch (err) {
            setError(err instanceof Error ? err.message : 'An unknown error occurred');
        } finally {
            setLoading(false);
        }
    };
    
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
                { activeTab === EntityIndex.PRODUCTS ? (
                    <SearchBar 
                        onSearch={(searchTerm)=>{setSearchTerm(searchTerm)}}
                        placeholderText='Search by product name or description...'
                    />
                ) : activeTab === EntityIndex.PRODUCT_VARIANTS ? (
                    <SearchBar 
                        onSearch={(searchTerm)=>{setSearchTerm(searchTerm)}}
                        placeholderText='Search by product variant attributes...'
                    />
                ) : activeTab === EntityIndex.ORDERS ? (
                    <SearchBar 
                        onSearch={(searchTerm)=>{setSearchTerm(searchTerm)}}
                        placeholderText='Search by order attributes...'
                    />
                ) : null }
                { activeTab === EntityIndex.PRODUCTS ? (
                    <div>
                        <button 
                            className="addButton"
                            onClick={togglePopup}
                        >
                            Add Product
                        </button>
                        <PopUp 
                            isOpen={isPopupOpen} 
                            title="Add Product"
                            onClose={togglePopup}
                            onSubmit={submitionHandler(
                                activeTab, 
                                tabs[activeTab].apiEndpoint, 
                                URL.PRODUCTS, 
                                RequestMethod.POST,
                                setProducts)
                            }
                        >
                            <input
                                type="text"
                                name="name"
                                placeholder="Enter product name"
                            />
                            <input
                                type="text"
                                name="description"
                                placeholder="Enter product description"
                            />
                        </PopUp>
                    </div>
                ) : activeTab === EntityIndex.PRODUCT_VARIANTS ? (
                    <div>
                        <button 
                            className="addButton"
                            onClick={togglePopup}
                        >
                            Add Product Variant
                        </button>
                        <PopUp 
                            isOpen={isPopupOpen} 
                            title="Add Product Variant"
                            onClose={togglePopup}
                            onSubmit={submitionHandler(
                                activeTab, 
                                tabs[activeTab].apiEndpoint, 
                                URL.PRODUCT_VARIANTS, 
                                RequestMethod.POST,
                                setProductVariants)
                            }
                        >
                            <select
                                className="selectDropdown"
                                name="productUuid"
                                required
                            >
                                <option value="null" selected disabled hidden>Select a product</option>
                                {
                                    products?.map((product) => (
                                        <option key={product.uuid} value={product.uuid}>{product.name}</option>
                                    ))
                                }
                            </select>
                            <select
                                className="selectDropdown"
                                name="colour"
                                required
                            >
                                <option value="null" selected disabled hidden>Select a colour</option>
                                <option key="RED" value="RED">Red</option>
                                <option key="GREEN" value="GREEN">Green</option>
                                <option key="BLUE" value="BLUE">Blue</option>
                            </select>
                            <select
                                className="selectDropdown"
                                name="size"
                                required
                            >
                                <option value="null" selected disabled hidden>Select a size</option>
                                <option key="L" value="L">Large</option>
                                <option key="M" value="M">Medium</option>
                                <option key="S" value="S">Small</option>
                            </select>
                            <input
                                type="text"
                                name="price"
                                placeholder="Enter product variant price"
                            />
                            <input
                                type="text"
                                name="stock"
                                placeholder="Enter product variant stock"
                            />
                        </PopUp>
                    </div>
                ) : activeTab === EntityIndex.ORDERS ? (
                    <div>
                        <button 
                            className="addButton"
                            onClick={togglePopup}
                        >
                            Add Order
                        </button>
                        <PopUp 
                            isOpen={isPopupOpen} 
                            title="Add Order"
                            onClose={togglePopup}
                            onSubmit={submitionHandler(
                                activeTab, 
                                tabs[activeTab].apiEndpoint, 
                                URL.ORDERS, 
                                RequestMethod.POST)
                            }
                        >
                            <select
                                className="selectDropdown"
                                name="productVariantUuid"
                                required
                            >
                                <option value="null" selected disabled hidden>Select a product variant</option>
                                {
                                    productVariants?.map((productVariant) => (
                                        <option key={productVariant.uuid} value={productVariant.uuid}>
                                            {productVariant.product.name} | {productVariant.sku}
                                        </option>
                                    ))
                                }
                            </select>
                        </PopUp>
                    </div>
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
                        activeTab === EntityIndex.PRODUCTS ? (
                            <TableComponent 
                                columns={ProductColumns} 
                                data={data} 
                                deletionHandler={deletionHandler(activeTab, tabs[activeTab].apiEndpoint)} 
                                updateHandler={submitionHandler(activeTab, tabs[activeTab].apiEndpoint, URL.PRODUCTS, RequestMethod.PUT)}
                                popUpFields={[
                                    { name: "name", label: "Update product name", placeholder: "Update name" },
                                    { name: "description", label: "Update product description", placeholder: "Update description" }
                                ]}
                            />
                        ) : activeTab === EntityIndex.PRODUCT_VARIANTS ? (
                            <TableComponent 
                                columns={ProductVariantColumns} 
                                data={data} 
                                deletionHandler={deletionHandler(activeTab, tabs[activeTab].apiEndpoint)}
                                updateHandler={submitionHandler(activeTab, tabs[activeTab].apiEndpoint, URL.PRODUCT_VARIANTS, RequestMethod.PUT)}
                                popUpFields={[
                                    { name: "stock", label: "Update product variant stock quantity", placeholder: "Update stock" },
                                    { name: "price", label: "Update product variant price", placeholder: "Update price" }
                                ]}
                            />
                        ) : activeTab === EntityIndex.ORDERS ? (
                            <TableComponent 
                                columns={OrdersColumns} 
                                data={data} 
                                deletionHandler={deletionHandler(activeTab, tabs[activeTab].apiEndpoint)} 
                                updateHandler={submitionHandler(activeTab, tabs[activeTab].apiEndpoint, URL.ORDERS, RequestMethod.PUT)}
                                popUpFields={[
                                    { name: "mapping", label: "Update order mapping", placeholder: "Update mapping" }
                                ]}
                            />
                        ) : null
                    }
                </div>
            </div>
        </div>
    );
};
  
export default TabsPanel;