import React, { Component } from 'react';
import '../Planes.css'
import { Form } from "react-bootstrap";

class SearchBar extends Component {
    render() {
        return (
            <Form onSubmit={this.props.handleSubmit()}>
                <button className="btn-sfr" type="submit"><i className="bi bi-search"></i></button>
                <input type="text" id="search" placeholder="Buscar"></input>
            </Form>
        );
    }
};
export default SearchBar;

