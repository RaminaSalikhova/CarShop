import React, {useEffect, useState} from 'react';
import PaginationComponent from "./PaginationComponent";
import axios from "axios";
import {Col, Row} from "react-bootstrap";
import CustomRow from "./CustomRow";

const Pagination = () => {

    const [totalRecords, setTotalRecords] = useState(0);
    const [data, setData] = useState([]);
    const [limit, setLimit] = useState(2);

    const [reload, setReloading] = useState(false);

    useEffect(async () => {
        await fetchData(0);
    }, []);

    async function fetchData(page) {
        let pathLimit = limit + 2;
        let url = "http://localhost:8082/carshop/advertisements/?page=" + page + "&size=" + pathLimit;
        console.log('uri>>', url);

        const response = await axios.get(url)
        console.log('response>>', response.data);
        console.log('getAdvertisementDto>>', response.data.getAdvertisementDto);
        setData(response.data);
        setTotalRecords(response.data.total);
        console.log('total>>', totalRecords);
    }


    const getPaginatedData = (page) => {
        fetchData(page);
    }

    return (
        <div>
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
                    getAllData={getPaginatedData}
                    totalRecords={totalRecords}
                    itemsCountPerPage={limit}/>
            }
        </div>
    );
};

export default Pagination;