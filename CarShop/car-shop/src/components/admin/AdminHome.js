import React, {useEffect, useState} from 'react';
import Pagination from "../pagination/Pagination"
import axios from "axios";
import UserItem from "./UserItem";

const AdminHome = () => {
    const[token, setToken]=useState(sessionStorage.getItem("token"));
    const[users, setUsers]=useState([]);

    useEffect(async ()=>{
        await fetchUsers();
    }, []);

    async function fetchUsers(){
        console.log(token)
        const response=await axios.get("http://localhost:8082/carshop/users/", {
            headers: {
                "Authorization": `Bearer ${token}`
            }})
        console.log(response.data)
        setUsers(response.data)
    }

    return (
        <div>
            <h1>Hi, Admin</h1>

            <h4 className="h4">Advertisements</h4>
            <div className="App">
                <Pagination></Pagination>
            </div>

            <h4 className="h4">Users</h4>
            {users.map(user=> <UserItem token={token} user={user}></UserItem>)}
        </div>
    );
};

export default AdminHome;