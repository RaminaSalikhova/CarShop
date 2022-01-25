import './App.css';
import React from "react";
import CustomTabs from "./components/tabs/CustomTabs";
import {Route, Switch, useHistory} from "react-router-dom";
import AdminHome from "./components/admin/AdminHome";
import UserHome from "./components/user/UserHome";

function App() {
    const history = useHistory();

    return (
        <Switch>
            <Route exact path="/adminHome" component={AdminHome}/>
            <Route exact path="/userHome" component={UserHome}/>
            <Route exact path="/">
                <div className="App">
                    <header></header>
                    <CustomTabs history={history}></CustomTabs>
                </div>
            </Route>
        </Switch>
    );
}

export default App;
