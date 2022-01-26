import React, {useEffect, useState} from 'react';
import {Card, Modal} from "react-bootstrap";
import axios from "axios";
import {Formik} from "formik";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import * as yup from "yup";
import ContactItem from "./ContactItem";


const AdvertisementItem = (props) => {
    const [show, setShow] = useState(false);

    const [reloadList, setReloadingList] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = async () => {
        setShow(true);
        console.log(props.advertisement.car)
    }

    const [showContacts, setShowContacts] = useState(false);

    const handleCloseContacts = () => setShowContacts(false);
    const handleShowContacts = async () => {
        setShowContacts(true);
        fetchContacts();
    }

    const [contacts, setContacts] = useState([]);

    async function fetchContacts() {
        let url = "http://localhost:8082/carshop/advertisements/" + props.advertisement.advertisementId + "/contacts/"
        const response = await axios.get(url, {
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("token")}`
            }
        });
        console.log(response.data);
        setContacts(response.data);
        setReloadingList(!reloadList);
    }

    async function postContact(obj) {
        let url = "http://localhost:8082/carshop/advertisements/"+ props.advertisement.advertisementId+"/contacts/";
        const response = await axios.post(url, obj, {
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("token")}`
            }
        });
        props.setReloadingList(!props.currentReloadState);
        setReloadingList(!reloadList);
    }


    async function deleteAdvertisement() {
        let url = "http://localhost:8082/carshop/advertisements/" + props.advertisement.advertisementId;
        const response = await axios.delete(url, {
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("token")}`
            }
        });
        props.setReloadingList(!props.currentReloadState);

    }

    async function updateAdvertisement(obj) {
        let url = "http://localhost:8082/carshop/advertisements/" + props.advertisement.advertisementId;
        const response = await axios.put(url, obj, {
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("token")}`
            }
        })
        console.log(response.data)
        props.setReloadingList(!props.currentReloadState);
    }

    const handleEdit = (e) => {
        let obj = {
            carId: props.advertisement.car.carId,
            mark: e.mark,
            model: e.model,
            yearofproduction: e.yearofproduction,
            state: e.state,
            mileage: e.mileage,
            costId: props.advertisement.cost.costId,
            value: e.cost,
            currency: e.currency,
            userId: sessionStorage.getItem("userId"),
        };
        console.log(obj)
        updateAdvertisement(obj);
    }
    const phoneRegExp = /^\+375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$/

    const handleAddContact = (e) => {
        const obj={
            number:e.phoneNumber,
        }
        console.log(obj);
        postContact(obj);
    }

    const schemaContacts = yup.object().shape({
        phoneNumber: yup.string().matches(phoneRegExp, 'Phone number is not valid'),
    });

    const schema = yup.object().shape({
        mark: yup.string().required("Fill mark!"),
        model: yup.string().required("Fill model!"),
        yearofproduction: yup.string().required("Choose year of production!"),
        state: yup.string().min(5, 'Too Short!').required("Choose state!"),
        mileage: yup.number().min(0).required("Fill mileage!"),
        cost: yup.number().min(1).required("Fill value!"),
        currency: yup.string().required("Choose currency!")
    });

    const yearOptions = [
        {value: "1990", label: "1990"},
        {value: "1991", label: "1991"},
        {value: "1992", label: "1992"},
        {value: "1993", label: "1993"},
        {value: "1994", label: "1994"},
        {value: "1995", label: "1995"},
        {value: "1996", label: "1996"},
        {value: "1997", label: "1997"},
        {value: "1998", label: "1998"},
        {value: "1999", label: "1999"},
        {value: "2000", label: "2000"},
        {value: "2001", label: "2001"},
        {value: "2002", label: "2002"},
        {value: "2003", label: "2003"},
        {value: "2004", label: "2004"},
        {value: "2005", label: "2005"},
        {value: "2006", label: "2006"},
        {value: "2007", label: "2007"},
        {value: "2008", label: "2008"},
        {value: "2009", label: "2009"},
        {value: "2010", label: "2010"},
        {value: "2011", label: "2011"},
        {value: "2012", label: "2012"},
        {value: "2013", label: "2013"},
        {value: "2014", label: "2014"},
        {value: "2015", label: "2015"},
        {value: "2016", label: "2016"},
        {value: "2017", label: "2017"},
        {value: "2018", label: "2018"},
        {value: "2019", label: "2019"},
        {value: "2020", label: "2020"},
        {value: "2021", label: "2021"},
        {value: "2022", label: "2022"}
    ];

    console.log(props);

    return (
        <div>
            <Card style={{width: '90%', margin: '3%'}}>
                <Card.Body>
                    <Card.Title>
                        <div>{props.advertisement.car.mark}</div>
                        <div>{props.advertisement.car.model}</div>
                    </Card.Title>
                    <Card.Text>
                        <div>Cost: {props.advertisement.cost.value} - {props.advertisement.cost.currency}</div>

                    </Card.Text>
                </Card.Body>
                <button onClick={handleShow} type="button" className="btn btn-dark" style={{margin: "1%"}}>Edit
                    advertisement
                </button>
                <button onClick={deleteAdvertisement} type="button" className="btn btn-dark"
                        style={{margin: "1%"}}>Delete advertisement
                </button>
                <button onClick={handleShowContacts} type="button" className="btn btn-dark" style={{margin: "1%"}}>Your
                    contacts
                </button>
            </Card>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>More about</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Formik
                        onSubmit={handleEdit}
                        validationSchema={schema}
                        initialValues={{
                            mark: props.advertisement.car.mark,
                            model: props.advertisement.car.model,
                            yearofproduction: props.advertisement.car.yearOfProduction,
                            state: props.advertisement.car.state,
                            mileage: props.advertisement.car.mileage,
                            cost: props.advertisement.cost.value,
                            currency: props.advertisement.cost.currency,
                        }}
                        render={({
                                     handleSubmit,
                                     handleChange,
                                     values,
                                     touched,
                                     errors
                                 }) => {
                            return (
                                <Form onSubmit={handleSubmit}>
                                    <Form.Group className="mb-3" controlId="mark">
                                        <Form.Label>Mark</Form.Label>
                                        <Form.Control
                                            onChange={handleChange}
                                            value={values.mark}
                                            name="mark"
                                            type="text"
                                            isValid={touched.mark && !errors.mark}/>
                                        {errors.mark && touched.mark ? (
                                            <div className="error" style={{color: "red"}}>{errors.mark}</div>) : null}
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="model">
                                        <Form.Label>Model</Form.Label>
                                        <Form.Control
                                            onChange={handleChange}
                                            value={values.model}
                                            name="model"
                                            type="text"
                                            isValid={touched.model && !errors.model}/>
                                        {errors.model && touched.model ? (
                                            <div className="error" style={{color: "red"}}>{errors.model}</div>) : null}
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="yearofproduction">
                                        <Form.Label>Select year of production</Form.Label>
                                        <Form.Control as="select"
                                                      onChange={handleChange}
                                                      value={values.yearofproduction}
                                                      name="yearofproduction"
                                                      isValid={touched.yearofproduction && !errors.yearofproduction}>
                                            <option>Open this select menu</option>
                                            {
                                                yearOptions.map(v => (
                                                    <option value={v.value}>{v.label}</option>
                                                ))
                                            }
                                        </Form.Control>

                                        {errors.yearofproduction && touched.yearofproduction ? (
                                            <div className="error"
                                                 style={{color: "red"}}>{errors.yearofproduction}</div>) : null}
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="state">
                                        <Form.Label>Select state</Form.Label>
                                        <Form.Control as="select"
                                                      onChange={handleChange}
                                                      name="state"
                                                      value={values.state}
                                                      isValid={touched.state && !errors.state}>
                                            <option>Open this select menu</option>
                                            <option value={"withMileage"}>with mileage</option>
                                            <option value={"withDamage"}>with damage</option>
                                            <option value={"onParts"}>with parts</option>
                                            <option value={"unused"}>with unused</option>
                                        </Form.Control>
                                        {errors.state && touched.state ? (
                                            <div className="error" style={{color: "red"}}>{errors.state}</div>) : null}
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="mileage">
                                        <Form.Label>Mileage</Form.Label>
                                        <Form.Control
                                            onChange={handleChange}
                                            value={values.mileage}
                                            name="mileage"
                                            type="number"
                                            isValid={touched.mileage && !errors.mileage}/>
                                        {errors.mileage && touched.mileage ? (
                                            <div className="error"
                                                 style={{color: "red"}}>{errors.mileage}</div>) : null}
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="value">
                                        <Form.Label>Cost</Form.Label>
                                        <Form.Control
                                            onChange={handleChange}
                                            value={values.cost}
                                            name="cost"
                                            type="numeric"
                                            isValid={touched.cost && !errors.cost}/>
                                        {errors.cost && touched.cost ? (
                                            <div className="error" style={{color: "red"}}>{errors.cost}</div>) : null}
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="currency">
                                        <Form.Label>Select currency</Form.Label>
                                        <Form.Control as="select"
                                                      onChange={handleChange}
                                                      value={values.currency}
                                                      name="currency"
                                                      isValid={touched.currency && !errors.currency}>
                                            <option>Open this select menu</option>
                                            <option value={"EUR"}>EUR</option>
                                            <option value={"BYN"}>BYN</option>
                                            <option value={"USD"}>USD</option>
                                        </Form.Control>
                                        {errors.currency && touched.currency ? (
                                            <div className="error"
                                                 style={{color: "red"}}>{errors.currency}</div>) : null}
                                    </Form.Group>

                                    <Button type="submit">
                                        <span>Submit</span>
                                    </Button>
                                </Form>);
                        }}>
                    </Formik>
                </Modal.Body>
            </Modal>


            <Modal show={showContacts} onHide={handleCloseContacts}>
                <Modal.Header closeButton>
                    <Modal.Title>More about</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <h4 className="h4">Your contacts</h4>
                    {contacts.map(contact =>
                        <ContactItem contact={contact}
                                     setReloadingList={setReloadingList}
                                     currentReloadState={reloadList}
                        ></ContactItem>
                    )}
                    <Formik
                        onSubmit={handleAddContact}
                        validationSchema={schemaContacts}
                        initialValues={{
                            phoneNumber: "",
                        }}
                        render={({
                                     handleSubmit,
                                     handleChange,
                                     values,
                                     touched,
                                     errors
                                 }) => {
                            return (
                                <Form onSubmit={handleSubmit}>
                                    <Form.Group className="mb-3" controlId="mark">
                                        <Form.Label>Phone Number</Form.Label>
                                        <Form.Control
                                            onChange={handleChange}
                                            name="phoneNumber"
                                            type="text"
                                            isValid={touched.phoneNumber && !errors.phoneNumber}/>
                                        {errors.phoneNumber && touched.phoneNumber ? (
                                            <div className="error"
                                                 style={{color: "red"}}>{errors.phoneNumber}</div>) : null}
                                    </Form.Group>

                                    <Button type="submit">
                                        <span>Submit</span>
                                    </Button>
                                </Form>);
                        }}>
                    </Formik>
                </Modal.Body>
            </Modal>

        </div>
    );
};

export default AdvertisementItem;