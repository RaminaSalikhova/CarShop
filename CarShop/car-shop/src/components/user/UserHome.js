import React, {useEffect, useState} from 'react';
import axios from "axios";
import AdvertisementItem from "./AdvertisementItem";
import {Modal, Navbar} from "react-bootstrap";
import Carousel from "react-bootstrap/Carousel";
import {Formik, Field, ErrorMessage} from "formik";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import * as yup from "yup";
import {useHistory} from "react-router-dom";

const UserHome = () => {

    const [advertisements, setAdvertisements] = useState([]);
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = async () => {
        setShow(true);
    }

    const [reloadList, setReloadingList] = useState(false);

    useEffect(async () => {
        await fetchAdvertisements();
    }, [reloadList]);

    async function fetchAdvertisements() {
        let url = "http://localhost:8082/carshop/users/" + sessionStorage.getItem("userId") + "/advertisements/"
        const response = await axios.get(url, {
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("token")}`
            }
        });
        console.log(response.data);
        setAdvertisements(response.data);
    }

    async function postAdvertisements(obj) {
        let url = "http://localhost:8082/carshop/advertisements/"
        const response = await axios.post(url, obj, {
            headers: {
                "Authorization": `Bearer ${sessionStorage.getItem("token")}`
            }
        });
        setReloadingList(!reloadList);
    }

    const handleAdd = (e) => {
        let obj = {
            mark: e.mark,
            model: e.model,
            yearofproduction: e.yearofproduction,
            state: e.state,
            mileage: e.mileage,
            value: e.cost,
            currency: e.currency,
            userId: sessionStorage.getItem("userId")
        };
        console.log(obj)
        postAdvertisements(obj);
    }

    const history = useHistory();

    const logout=()=>{
        sessionStorage.removeItem('token');
        sessionStorage.removeItem('userId');
        sessionStorage.removeItem('role');
        history.push("/");
    }

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

    return (
        <div>
                <Navbar bg="dark" variant="dark">
                    <Navbar.Brand>
                        <h1>Hi, User</h1>
                    </Navbar.Brand>
                    <Navbar.Toggle/>
                    <Navbar.Collapse className="justify-content-end">
                        <button onClick={logout} type="button" className="btn btn-dark" style={{margin: "3.5%"}}>
                            Log out
                        </button>
                    </Navbar.Collapse>
                </Navbar>

            <button onClick={handleShow} type="button" className="btn btn-dark" style={{margin: "3.5%"}}>Add
                advertisement
            </button>

            <h4 className="h4">Your advertisements</h4>
            {advertisements.map(advertisement =>
                advertisement.activityStatus == "deactivated"
                    ? null
                    :
                    <AdvertisementItem key={advertisement.advertisementId}
                                       token={sessionStorage.getItem("token")}
                                       advertisement={advertisement}
                                       setReloadingList={setReloadingList}
                                       currentReloadState={reloadList}
                    />)}

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>More about</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Formik
                        onSubmit={handleAdd}
                        validationSchema={schema}
                        initialValues={{
                            mark: "",
                            model: "",
                            yearofproduction: "",
                            state: "",
                            mileage: 0,
                            cost: 0,
                            currency: ""
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
        </div>
    );
};

export default UserHome;