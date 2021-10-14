import React, { Component } from 'react';
import axios from 'axios';
import '../Planes.css'
import {Form } from "react-bootstrap";
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
            url: `http://localhost:8080/SFR/API/PlanSearch`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'searchPlan': event.target.searchPlan.value
            }
        }

        axios(options)
            .then(response => {
                this.props.updatePlanes(response.data);
                console.log(response.data);
                console.log(response.data.name);
            }).catch(error => {
                console.log(error);
                toast.error("Funca!", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 10000
                });
            });

            

    }


    render() {

        return (
            <Form onSubmit={this.handleSubmit} className='ms-auto'>
                
                <button  className="btn-sfr" type="submit">Buscar</button>
                <input type="text" id="searchPlan" name="searchPlan" placeholder="Buscar"></input>
               
            </Form>
        );
    }
};
export default Search;

