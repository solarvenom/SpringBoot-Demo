import { Dispatch, SetStateAction } from "react";
import { RequestMethod } from "../enums"

export const generateSubmitionHandler = (
        setter: (tabIndex: number, data:[]) => void
    ) => {
        return (
            activeTab: number, 
            refreshEndpoint: string,
            submitEndpoint: string,
            method: string,
            entitySetter?: Dispatch<SetStateAction<any>>
        ) => {
            return async (dataToSubmit: any, uuid?: string) => {
                for(const key of Object.keys(dataToSubmit)){
                    if(dataToSubmit[key] == "" || dataToSubmit[key] == undefined || dataToSubmit[key] == null){
                        alert(`Field "${key}" cannot be empty.`)
                        return
                    }
                }
                await fetch(uuid ? `${submitEndpoint}/${uuid}` : submitEndpoint, {
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
                if(entitySetter) entitySetter(data);
            }
        }
    }