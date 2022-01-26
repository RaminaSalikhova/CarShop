import React from 'react';
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import axios from "axios";
import {Formik, Field, ErrorMessage} from 'formik';
import * as yup from 'yup';

const Login = (props) => {

    async function loginUser(obj) {
        const response = await axios.post("http://localhost:8082/carshop/users/login", obj)
        console.log(response.data)

        if (response.data !== undefined) {
            sessionStorage.setItem("token", response.data.token)
            sessionStorage.setItem("userId", response.data.userId)
            sessionStorage.setItem("role", response.data.role)
            redirectToUserHome()
        }
    }

    const handleLogin = (e) => {
        let obj = {
            email: e.email,
            password: e.password
        };
        loginUser(obj);
    }

    const redirectToUserHome = () => {
        if (sessionStorage.getItem("role") === 'user') {
            props.history.push("/userHome");

        } else if (sessionStorage.getItem("role") === 'admin') {
            props.history.push("/adminHome");
        }
    }

    const schema = yup.object().shape({
        password: yup.string().required("Fill password!"),
        email: yup.string().required("Fill email!")
    });

    return (
        <div>
            <h1>Sign in</h1>
            <Formik
                onSubmit={handleLogin}
                validationSchema={schema}
                initialValues={{
                    email: "",
                    password: "",
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
                                    <div className="error">{errors.email}</div>) : null}
                                <Form.Text className="text-muted">
                                </Form.Text>
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control
                                    onChange={handleChange}
                                    value={values.password}
                                    name="password"
                                    type="password"
                                    isValid={touched.password && !errors.password}/>
                                {errors.password && touched.password ? (
                                    <div className="error">{errors.password}</div>) : null}
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

export default Login;