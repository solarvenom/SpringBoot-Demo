export interface PopUpProps {
    isOpen: boolean;
    title: string;
    children: React.ReactNode;
    onClose: () => void;
    onSubmit: (data: any) => any;
}
