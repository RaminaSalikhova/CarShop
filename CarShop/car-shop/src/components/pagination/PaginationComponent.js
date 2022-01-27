import React, {useEffect, useState} from 'react';
import Pagination from "react-js-pagination";

const PaginationComponent = (props) => {

    // const [activePage, setActivePage] = useState(1);

    const handlePageChange = (e) => {
        props.setActivePage(e-1);
        // props.getAllData(e-1);
        console.log( e-1);
        props.setReloadingList(!props.currentReloadState);
    };

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
                activePage={props.activePage}
                itemsCountPerPage={props.itemsCountPerPage}
                totalItemsCount={props.totalRecords}
                onChange={handlePageChange}
            />
        </div>
    );
};

export default PaginationComponent;