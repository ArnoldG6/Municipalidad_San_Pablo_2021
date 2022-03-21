import React, { Component } from 'react';
import { Form, Pagination, Stack } from "react-bootstrap";

class Pages extends Component {
    constructor(props) {
        super(props);
        this.handlePages = this.handlePages.bind(this);
        this.createItemsPC = this.createItemsPC.bind(this);
        this.createItemsMobile = this.createItemsMobile.bind(this);
    }

    createItemsPC(max) {
        if (max < 9) {
            return Array.from({ length: max }, (_, i) => i + 1);
        } else {
            let items = [];
            if (this.props.currentPage <= 5) {
                items = Array.from({ length: 7 }, (_, i) => i + 1);
                items.push(-1);
                items.push(max);
            } else if (this.props.currentPage >= (max - 5)) {
                items.push(1);
                items.push(-1);
                items.push(max - 6)
                items.push(max - 5);
                items.push(max - 4);
                items.push(max - 3);
                items.push(max - 2);
                items.push(max - 1);
                items.push(max);
            } else {
                items.push(1);
                items.push(-1);
                items.push(this.props.currentPage - 2);
                items.push(this.props.currentPage - 1);
                items.push(this.props.currentPage);
                items.push(this.props.currentPage + 1);
                items.push(this.props.currentPage + 2);
                items.push(-1);
                items.push(max);
            }
            return items;
        }
    }

    createItemsMobile(max) {
        if (max < 7) {
            return Array.from({ length: max }, (_, i) => i + 1);
        } else {
            let items = [];
            if (this.props.currentPage <= 3) {
                items = Array.from({ length: 5 }, (_, i) => i + 1);
                items.push(-1);
                items.push(max);
            } else if (this.props.currentPage >= (max - 3)) {
                items.push(1);
                items.push(-1);
                items.push(max - 4);
                items.push(max - 3);
                items.push(max - 2);
                items.push(max - 1);
                items.push(max);
            } else {
                items.push(1);
                items.push(-1);
                items.push(this.props.currentPage - 1);
                items.push(this.props.currentPage);
                items.push(this.props.currentPage + 1);
                items.push(-1);
                items.push(max);
            }
            return items;
        }
    }

    handlePages(pageNumber) {
        if (this.props.currentPage !== pageNumber) {
            this.props.updatePage(pageNumber);
        }
    }

    handleChange = e => {
        this.props.updatePageItems(e.target.value);
    }

    render() {
        let maxPages = Math.ceil(this.props.listLength / this.props.itemAmount);
        return (
            <div>
                {/* Mobile */}
                <div className='mt-2 d-lg-none'>
                    <Stack direction="vertical" gap={3}>
                        <Stack direction="horizontal" gap={2}>
                            <label>Mostrando</label>
                            <Form.Select defaultValue={10} style={{ width: '80px' }} onChange={this.handleChange}>
                                <option>10</option>
                                <option>15</option>
                                <option>20</option>
                            </Form.Select>
                            <label>elementos</label>
                        </Stack>
                        <Pagination>
                            <Pagination.First onClick={() => { this.handlePages(1) }} />
                            <Pagination.Prev onClick={() => { let page = this.props.currentPage; page > 1 ? this.handlePages(page - 1) : this.handlePages(1) }} />
                            {

                                this.createItemsMobile(maxPages).map((item) => {
                                    if (item > 0) {
                                        return <Pagination.Item active={this.props.currentPage === item ? true : false} key={item} onClick={() => { this.handlePages(item) }}>{item}</Pagination.Item>
                                    } else {
                                        return <Pagination.Ellipsis disabled></Pagination.Ellipsis>
                                    }
                                })
                            }
                            <Pagination.Next onClick={() => { let page = this.props.currentPage; page === maxPages ? this.handlePages(page) : this.handlePages(page + 1) }} />
                            <Pagination.Last onClick={() => { this.handlePages(maxPages) }} />
                        </Pagination>
                    </Stack>
                </div>
                {/* PC */}
                <div className="d-none d-lg-block">
                    <Stack direction="horizontal" gap={2}>
                        <label>Mostrando</label>
                        <Form.Select defaultValue={10} style={{ width: '80px' }} onChange={this.handleChange}>
                            <option>10</option>
                            <option>15</option>
                            <option>20</option>
                        </Form.Select>
                        <label>elementos</label>

                        <Pagination className="ms-auto">
                            <Pagination.First onClick={() => { this.handlePages(1) }} />
                            <Pagination.Prev onClick={() => { let page = this.props.currentPage; page > 1 ? this.handlePages(page - 1) : this.handlePages(1) }} />
                            {
                                this.createItemsPC(maxPages).map((item) => {
                                    if (item > 0) {
                                        return <Pagination.Item active={this.props.currentPage === item ? true : false} key={item} onClick={() => { this.handlePages(item) }}>{item}</Pagination.Item>
                                    } else {
                                        return <Pagination.Ellipsis disabled></Pagination.Ellipsis>
                                    }
                                })
                            }
                            <Pagination.Next onClick={() => { let page = this.props.currentPage; page === maxPages ? this.handlePages(page) : this.handlePages(page + 1) }} />
                            <Pagination.Last onClick={() => { this.handlePages(maxPages) }} />
                        </Pagination>
                    </Stack>
                </div>
            </div>
        );
    }
};
export default Pages;
