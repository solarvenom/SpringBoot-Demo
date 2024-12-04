import { RequestMethod } from "../enums"

export const generateSubmitionHandler = (
        setter: (tabIndex: number, data:[]) => void
    ) => {
        return (
            activeTab: number, 
            refreshEndpoint: string,
            submitEndpoint: string,
            method: string
        ) => {
            return async (dataToSubmit: any) => {
                await fetch(submitEndpoint, {
                    headers: {
                        'Accept': 'application/json, text/plain',
                        'Content-Type': 'application/json;charset=UTF-8'
                    },
                    method: method,
                    body: JSON.stringify(dataToSubmit)
                });
                
                const updatedData = await fetch(refreshEndpoint, { method: RequestMethod.GET });
                const data = await updatedData.json()
                setter(activeTab, data);
            }
        }
    }
        