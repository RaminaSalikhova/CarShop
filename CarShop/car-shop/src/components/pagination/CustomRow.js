import React, {useEffect, useState} from 'react';
import {Col, Modal, Row} from "react-bootstrap";
import Carousel from "react-bootstrap/Carousel";
import axios from "axios";

const CustomRow = (props) => {

    const [imageObjectURL, setImageObjectURL] = useState('');
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = async () => {
        setShow(true);
        const photos = await fetchPhotosPaths();
        await fetchPhoto(photos[0].path);
    }


    async function fetchPhotosPaths() {
        let url = "http://localhost:8082/carshop/cars/" + props.item.car.carId + "/photos/";
        console.log('path>>', url);
        const response = await axios.get(url)
        console.log('response>>', response.data);
        console.log('car id>>', props.item.car.carId);

        return response.data;
    }

    function base64ToArrayBuffer(base64) {
        let binaryString = window.atob(base64);
        let binaryLen = binaryString.length;
        let bytes = new Uint8Array(binaryLen);
        for (let i = 0; i < binaryLen; i++) {
            let ascii = binaryString.charCodeAt(i);
            bytes[i] = ascii;
        }
        return bytes;
    }

    function saveByteArray(byte, contentType) {
        let blob = new Blob([byte], {type: contentType});
        const imageURL = URL.createObjectURL(blob);
        setImageObjectURL(imageURL);
    };

    async function fetchPhoto(filename) {
        let url = "http://localhost:8082/carshop/photos/" + filename + "/";
        const response = await axios.get(url);

        const arrayBytes = base64ToArrayBuffer(response.data);
        saveByteArray(arrayBytes, 'image/jpeg');
    }

    return (
        <div>

            <Row key={props.item.advertisementId}>
                <Col>
                    {props.index}
                </Col>
                <Col>
                    {props.item.car.mark} {} {props.item.car.model}
                </Col>
                <Col>
                    {props.item.car.state}
                </Col>
                <Col>
                    {props.item.car.mileage}
                </Col>
                <Col>
                    {props.item.car.yearOfProduction}
                </Col>
                <Col>
                    {props.item.cost.value} {} {props.item.cost.currency}
                </Col>
                <Col>
                    <button onClick={handleShow} type="button" className="btn btn-dark">Load more</button>
                </Col>
            </Row>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>More about</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Carousel>
                        {
                            imageObjectURL.length !== 0 ?
                                <Carousel.Item>
                                    <img
                                        className="d-block w-100"
                                        src={imageObjectURL}
                                        alt="First slide"
                                    />
                                </Carousel.Item>
                                : null
                        }
                    </Carousel>
                    <p>Seller - {props.item.name}</p>
                    <p>Contacts:</p>
                    {
                        Array.from({length: props.item.contact.length}).map((_, index) => (
                            <li>
                                {
                                    props.item.contact[index]
                                }
                            </li>
                        ))
                    }
                </Modal.Body>
            </Modal>
        </div>
    );
};

export default CustomRow;