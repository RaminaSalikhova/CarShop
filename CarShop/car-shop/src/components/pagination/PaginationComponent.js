import React, {useEffect, useState} from 'react';
import Pagination from "react-js-pagination";

const PaginationComponent = (props) => {

    const [totalRecords, setTotalRecords] = useState(0);
    const [activePage, setActivePage] = useState(1);
    const [limit, setLimit] = useState(2);


    const handlePageChange = (e) => {
        setActivePage(e-1);
        props.getAllData(e-1);
        console.log( e-1);
        props.setReloadingList(!props.currentReloadState);
    };

    useEffect(async () => {
        setTotalRecords(props.totalRecords)
    })

    return (
        <div className="pagination-wrapper" style={{marginLeft: "30%"}}>
            <Pagination
                aria-label="Page navigation example"
                itemClass="page-item"
                linkClass="page-link"
                prevPageText="Prev"
                nextPageText="Next"
                firstPageText="First"
                lastPageText="Last"
                activePage={activePage}
                itemsCountPerPage={limit}
                totalItemsCount={totalRecords}
                onChange={handlePageChange}
            />
        </div>
    );
};

export default PaginationComponent;