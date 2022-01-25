import React from 'react';
import {Tabs} from "react-bootstrap"
import {Tab} from "react-bootstrap"
import Pagination from "../pagination/Pagination"
import Login from "../login/Login";
import Registration from "../login/Registration";

const CustomTabs = () => {
    return (
        <div>
            <Tabs  id="uncontrolled-tab-example" className="mb-3">
                <Tab eventKey="Store" title="Store" >
                    <Pagination/>
                </Tab>
                <Tab eventKey="SignIn" title="Sign in">
                    <Login/>
                </Tab>
                <Tab eventKey="SignUp" title="Sign up">
                    <Registration/>
                </Tab>
            </Tabs>
        </div>
    );
};

export default CustomTabs;