import { myAxios } from "../Helper/Helper";

export const signUp = (user)=>{

    return myAxios.post('/api/register',user)
    .then((response)=> response.data)
}