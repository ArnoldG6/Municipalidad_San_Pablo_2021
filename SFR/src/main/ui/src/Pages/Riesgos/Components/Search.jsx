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
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 406:
                            msj = "Hubo un problema recuperando los datos solicitados.";
                            break;
                        case 500:
                            msj = "El servidor ha encontrado un error desconocido.";
                            break;
                        default:
                            msj = "El servidor ha encontrado un error desconocido.";
                            break;
                    }
                } else if (error.request) {
                    //Server did not respond
                    msj = "Hubo un error con la conexión al servidor."
                } else {
                    //Something else went wrong
                    msj = "Error desconocido."
                }
                toast.error(msj, {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            })
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

