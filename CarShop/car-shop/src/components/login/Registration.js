import React, {useState} from 'react';
import axios from "axios";
import Form from 'react-bootstrap/Form';
import {Formik, Field, ErrorMessage} from 'formik';
import * as yup from 'yup';
import {Alert, Container} from "react-bootstrap";
import Button from 'react-bootstrap/Button'

const Registration = (props) => {

    const [show, setShow] = useState(false);

    async function register(obj) {
        const response = await axios.post("http://localhost:8082/carshop/users/register", obj)
        console.log(response.data)
        if (response.data === "Successful operation") {
            setShow(true);
        }
    }

    const handleLogin = (e) => {
        console.log(e)
        let obj = {
            name: e.name,
            surname: e.surname,
            email: e.email,
            password: e.password,
        };

        console.log(obj);
        register(obj);
    }

    const schema = yup.object().shape({

        name: yup.string().required("Fill first name!"),
        surname: yup.string().required("Fill last name!"),
        password: yup.string().required("Fill password!")
            .min(5, 'Too Short!'),
        email: yup.string().required("Fill email!")
    });

    return (
        <div>
            <h1>Sign up</h1>
            {show ? <Alert variant='warning'>
                    <Alert.Heading> Successful!</Alert.Heading>
                    Please, log in
                </Alert> :
                <div></div>
            }
            <Formik
                onSubmit={handleLogin}
                validationSchema={schema}
                initialValues={{
                    name: "",
                    surname: "",
                    email: "",
                    password: ""
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
                            <Form.Group className="mb-3" controlId="formBasicEmail">
                                <Form.Label>Email address</Form.Label>
                                <Form.Control
                                    onChange={handleChange}
                                    value={values.email}
                                    name="email"
                                    type="email"
                                    isValid={touched.email && !errors.email}/>
                                {errors.email && touched.email ? (
                                    <div className="error" style={{color:"red"}}>{errors.email}</div>) : null}
                                <Form.Text className="text-muted">
                                </Form.Text>
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control onChange={handleChange}
                                              value={values.password}
                                              name="password"
                                              type="password"
                                              isValid={touched.password && !errors.password}/>
                                {errors.password && touched.password ? (
                                    <div className="error" style={{color:"red"}}>{errors.password}</div>) : null}
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>First name</Form.Label>
                                <Form.Control
                                    onChange={handleChange}
                                    value={values.name}
                                    name="name"
                                    type="text"
                                    isValid={touched.name && !errors.name}/>
                                {errors.name && touched.name ? (
                                    <div className="error" style={{color:"red"}}>{errors.name}</div>) : null}
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Last name</Form.Label>
                                <Form.Control
                                    onChange={handleChange}
                                    value={values.surname}
                                    name="surname"
                                    type="text"
                                    isValid={touched.surname && !errors.surname}/>
                                {errors.surname && touched.surname ? (
                                    <div className="error" style={{color:"red"}}>{errors.surname}</div>) : null}
                            </Form.Group>

                            <Button type="submit">
                                <span>Submit</span>
                            </Button>
                        </Form>);
                }}>
            </Formik>
        </div>
    );
};

export default Registration;