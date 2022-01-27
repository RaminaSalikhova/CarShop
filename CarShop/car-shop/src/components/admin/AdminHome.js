import React, {useEffect, useState} from 'react';
import Pagination from "../pagination/Pagination"
import axios from "axios";
import UserItem from "./UserItem";
import {Navbar} from "react-bootstrap";
import {useHistory} from "react-router-dom";

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

    const history = useHistory();

    const logout=()=>{
        sessionStorage.removeItem('token');
        sessionStorage.removeItem('userId');
        sessionStorage.removeItem('role');
        history.push("/");
    }

    return (
        <div>
            <Navbar bg="dark" variant="dark">
                <Navbar.Brand>
                    <h1>Hi, Admin</h1>
                </Navbar.Brand>
                <Navbar.Toggle/>
                <Navbar.Collapse className="justify-content-end">
                    <button onClick={logout} type="button" className="btn btn-dark" style={{margin: "3.5%"}}>
                        Log out
                    </button>
                </Navbar.Collapse>
            </Navbar>

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