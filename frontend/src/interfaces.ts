export interface ApiData {
    id: number;
    title: string;
    body: string;
};
  
export interface Tab {
    id: number;
    label: string;
    apiEndpoint: string;
};

export interface SearchBarProps {
    onSearch: (value: string) => void;
    placeholderText: string;
}

export interface PopUpProps {
    isOpen: boolean;
    title: string;
    children: React.ReactNode;
    onClose: () => void;
}
  