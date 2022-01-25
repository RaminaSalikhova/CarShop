import React from 'react';
import {Card} from "react-bootstrap";

const UserItem = (props) => {
    return (
        <div>
            <Card style={{ width: '90%' , margin:'3%'}}>
                <Card.Body>
                    <Card.Title>
                        <div>{props.user.name}</div>
                        <div>{props.user.surname}</div>
                    </Card.Title>
                    <Card.Text>
                        <div>Email: {props.user.email}</div>
                    </Card.Text>
                </Card.Body>
            </Card>
        </div>
    );
};

export default UserItem;