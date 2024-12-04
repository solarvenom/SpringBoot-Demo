import { RequestMethod } from "../enums";

export const generateDeletionHandler = (
        setter: (tabIndex: number, data:[]) => void
    ) => {
        return (
            activeTab: number, 
            refreshEndpoint: string
        ) => {
            return async (deletinEndpoint: string, uuid: string) => {
                try {
                    const response = await fetch(`${deletinEndpoint}/${uuid}`, {
                        method: RequestMethod.DELETE
                    });
                    
                    if (!response.ok) {
                        alert(`Failed to delete entity with UUID ${uuid}.`);
                    }
                    const updatedData = await fetch(refreshEndpoint, { method: RequestMethod.GET});
                    const data = await updatedData.json()
                    setter(activeTab, data);
                } catch (error) {
                    console.error("Error deleting entity:", error);
                    alert(`Error deleting entity with UUID ${uuid}.`);
                }
            }
        }
        
};
