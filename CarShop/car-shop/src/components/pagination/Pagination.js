import React, {useEffect, useState} from 'react';
import PaginationComponent from "./PaginationComponent";
import axios from "axios";
import {Col, Container, DropdownButton, Form, FormControl, Nav, Navbar, Row} from "react-bootstrap";
import CustomRow from "./CustomRow";
import {Dropdown} from "react-bootstrap";

const Pagination = () => {

    const [totalRecords, setTotalRecords] = useState(0);
    const [data, setData] = useState([]);
    const [limit, setLimit] = useState(3);
    const [sortParam, setSortParam] = useState("car.mark");
    const [filtrationParam, setFiltrationParam] = useState("");

    const [reload, setReloading] = useState(false);
    const [activePage, setActivePage] = useState(0);

    useEffect(async () => {
        await fetchData(activePage);
    }, [sortParam, reload, filtrationParam]);

    async function fetchData(page) {
        // let url = "http://localhost:8082/carshop/advertisements/?page=" + page + "&size=" + limit;
        let url="http://localhost:8082/carshop/advertisements/?page=" + page + "&size=" + limit+"&sort="+sortParam+",asc&filtrationValue="+filtrationParam+"";
        console.log('uri>>', url);

        const response = await axios.get(url)
        console.log('response>>', response.data);
        console.log('getAdvertisementDto>>', response.data.getAdvertisementDto);
        setData(response.data);
        setTotalRecords(response.data.total);
        console.log('total>>', totalRecords);
    }
    //
    // const getPaginatedData = (page) => {
    //     fetchData(activePage);
    // }

    const handleSort = async (e) => {
        // let url = "http://localhost:8082/carshop/advertisements/?size=" + limit + "&sort=" + e + ",asc";
        // console.log('uri>>', url);
        // const response = await axios.get(url)
        // setData(response.data);
        // setTotalRecords(response.data.total);
        setSortParam(e);
    }
    const handleFiltration = (e) => {
        // let url = "http://localhost:8082/carshop/advertisements/?size=" + limit + "&filtrationValue=" + e + "";
        // console.log('uri>>', url);
        // const response = await axios.get(url)
        // setData(response.data);
        // setTotalRecords(response.data.total);
        e.preventDefault();
        console.log(e.target);
        setFiltrationParam(e.target.searchValue.value);
    }

    return (
        <div>
                {/*<Dropdown style={{margin: "1%"}}>*/}
                {/*    <Dropdown.Toggle className="btn btn-dark" id="dropdown-basic">*/}
                {/*        Sort by*/}
                {/*    </Dropdown.Toggle>*/}
                {/*    <Dropdown.Menu onClick={handleSort}>*/}
                {/*        <Dropdown.Item eventKey="cost">Cost</Dropdown.Item>*/}
                {/*        <Dropdown.Item eventKey="model">Model</Dropdown.Item>*/}
                {/*    </Dropdown.Menu>*/}
                {/*</Dropdown>*/}

                <Navbar bg="light" expand="lg">
                    <Container fluid>
                        <Navbar.Collapse>
                            <Nav
                                className="me-auto my-2 my-lg-0"
                                style={{maxHeight: '100px'}}
                                navbarScroll
                            >
                                <DropdownButton title='Sort by' style={{margin: "1%"}} variant="dark"
                                                onSelect={handleSort}>
                                    <Dropdown.Item eventKey='car.model'>Model</Dropdown.Item>
                                    <Dropdown.Item eventKey='car.mark'>Mark</Dropdown.Item>
                                </DropdownButton>
                            </Nav>
                            <Form className="d-flex"
                                  onSubmit={handleFiltration}>
                                <FormControl
                                    type="search"
                                    placeholder="Search"
                                    className="me-2"
                                    aria-label="Search"
                                    name="searchValue"
                                />
                                <button className="btn btn-dark" type="submit">Search</button>
                            </Form>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>

                {/*<DropdownButton title='Sort by' style={{margin: "1%"}} variant="dark" onSelect={handleSort}>*/}
                {/*    <Dropdown.Item eventKey='cost.value'>Cost</Dropdown.Item>*/}
                {/*    <Dropdown.Item eventKey='car.mark'>Mark</Dropdown.Item>*/}
                {/*</DropdownButton>*/}

                {/*<DropdownButton title='Filtration by' style={{margin: "1%"}} variant="dark" onSelect={handleFiltration}>*/}
                {/*    <Dropdown.Item eventKey='cost.value'>Cost</Dropdown.Item>*/}
                {/*    <Dropdown.Item eventKey='car.mark'>Mark</Dropdown.Item>*/}
                {/*    <Dropdown.Item eventKey='car.model'>Model</Dropdown.Item>*/}
                {/*</DropdownButton>*/}

                {/*<Form className="d-flex">*/}
                {/*    <FormControl*/}
                {/*        type="search"*/}
                {/*        placeholder="Search"*/}
                {/*        className="me-2"*/}
                {/*        aria-label="Search"*/}
                {/*    />*/}
                {/*    <button className="btn btn-dark" onClick={handleFiltration}>Search</button>*/}
                {/*</Form>*/}

                {/*<Dropdown style={{margin: "1%"}}>*/}
                {/*    <Dropdown.Toggle className="btn btn-dark" id="dropdown-basic">*/}
                {/*        Filtration by*/}
                {/*    </Dropdown.Toggle>*/}
                {/*    <Dropdown.Menu>*/}
                {/*        <Dropdown.Item href="#/action-1">mark</Dropdown.Item>*/}
                {/*        <Dropdown.Item href="#/action-2">cost</Dropdown.Item>*/}
                {/*        <Dropdown.Item href="#/action-3">model</Dropdown.Item>*/}
                {/*    </Dropdown.Menu>*/}
                {/*</Dropdown>*/}

            <Row>
                <Col style={{width: "15%"}}><h6>Index</h6></Col>
                <Col><h6>Mark and model</h6></Col>
                <Col><h6>State</h6></Col>
                <Col><h6>Mileage</h6></Col>
                <Col><h6>Year of production</h6></Col>
                <Col><h6>Cost</h6></Col>
                <Col><h6></h6></Col>
            </Row>
            <table className="table">
                <thead></thead>
                {
                    data && data.total > 0 ?
                        data.getAdvertisementDto.map((item, index) => (
                            <CustomRow style={{width: "100%"}} item={item} index={index}/>
                        )) :
                        <tr>
                            <td>
                                <h4>No Data Found!!</h4>
                            </td>
                        </tr>
                }
            </table>
            {
                <PaginationComponent
                    setReloadingList={setReloading}
                    currentReloadState={reload}
                    // getAllData={getPaginatedData}
                    totalRecords={totalRecords}
                    activePage={activePage + 1}
                    setActivePage={setActivePage}
                    itemsCountPerPage={limit}/>
            }
        </div>
    );
};

export default Pagination;