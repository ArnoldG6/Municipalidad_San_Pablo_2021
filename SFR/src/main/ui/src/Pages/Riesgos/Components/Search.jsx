import React, { Component } from 'react';
import axios from 'axios';
import '../Riesgos.css'
import { Form } from "react-bootstrap";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class Search extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false
        };
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleSubmit = (event) => {
        event.preventDefault();

        let options = {
            url: process.env.REACT_APP_SFR_API_URL + `/RiskServlet/Search`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'searchRisk': event.target.searchRisk.value
            }
        }

        axios(options)
            .then(response => {
                this.props.updateRiesgos(response.data);
            }).catch(error => {
                console.log(error);
                toast.error("Error al realizar la busqueda", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            });



    }


    render() {

        return (
            <Form onSubmit={this.handleSubmit} className='ms-auto'>

                <button className="btn-sfr" type="submit"><i className="bi bi-search"></i></button>
                <input type="text" id="searchRisk" name="searchRisk" placeholder="Buscar"></input>

            </Form>
        );
    }
};
export default Search;

