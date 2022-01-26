import React from 'react';
import {Card} from "react-bootstrap";
import axios from "axios";

const ContactItem = (props) => {

    async function deleteContact() {
        let url = "http://localhost:8082/carshop/contacts/" + props.contact.contactId;
        const response = await axios.delete(url, {
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("token")}`
            }
        });
        props.setReloadingList(!props.currentReloadState);
    }

    return (
        <div>
            <Card style={{width: '90%', margin: '3%'}}>
                <Card.Body>
                    <Card.Text>
                        {props.contact.number}
                    </Card.Text>
                    <button onClick={deleteContact} type="button" className="btn btn-dark"
                            style={{margin: "1%"}}>Delete advertisement
                    </button>
                </Card.Body>
            </Card>
        </div>
    );
};

export default ContactItem;