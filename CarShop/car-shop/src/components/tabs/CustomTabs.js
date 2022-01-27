import React from 'react';
import {Tabs} from "react-bootstrap"
import {Tab} from "react-bootstrap"
import Pagination from "../pagination/Pagination"
import Login from "../login/Login";
import Registration from "../login/Registration";

const CustomTabs = (props) => {
    return (
        <div>
            <Tabs  id="uncontrolled-tab-example" className="mb-3">
                <Tab eventKey="Store" title="Store" >
                    <Pagination history={props.history}/>
                </Tab>
                <Tab eventKey="SignIn" title="Sign in">
                    <Login history={props.history}/>
                </Tab>
                <Tab eventKey="SignUp" title="Sign up">
                    <Registration history={props.history}/>
                </Tab>
            </Tabs>
        </div>
    );
};

export default CustomTabs;