import React, { Component, useState } from 'react';
import axios from 'axios';

import { Modal, Button, Form, Input, FormGroup } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../Riesgos.css'
class AddRiskModal extends Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.state = {
            value: "externo"
        };
    }

    //closeModal() { }

    handleSubmit = (event) => {
        event.preventDefault();

        let options = {
            url: `http://localhost:8080/SFR/API/RiskManager/insert`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'name': event.target.name.value,
                'probability': event.target.probability.value,
                'area-type': event.target.areatype.value,
                'impact': event.target.impact.value,
                'generalType': event.target.value.value,
                'description': event.target.factor.value,
                'specType' : event.target.specific_factor.value
            }
        }
      
        axios(options)
            .then(response => {
                this.props.updateRiesgos("add-success");
                this.props.closeModal();
            }).catch(error => {
                toast.error("ID del riesgo ya se encuentra registrado en el sistema.", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 10000
                });
            });

    }

    onChange = e => {
        this.setState({ value: e.target.value })
    }

    numberValidation (e){

        
    }

    render() {

        const { value } = this.state;


        let render = this.props.show
        let closeModal = this.props.closeModal
        return (
            <Modal show={render} onHide={closeModal} id="modalRisks" >
                <Modal.Header closeButton>
                    Nuevo Riesgo
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={this.handleSubmit}>

                        <div className="form-group">
                            <label>Nombre: </label>
                            <input name="name" id="name" type="text" placeholder="Nombre" className="form-control" required />
                        </div>

                        <div className="form-group">
                            <div className="number-input-container">
                                <label>Probabilidad: </label>
                                <input step=".1" min="0" max="1" name="probability" id="probability" type="number" placeholder="0.0" className="form-control number-input " required />
                            </div>
                            <div className="number-input-container">
                                <label>Impacto:</label>
                                <input min="0" max="10" name="impact" id="impact" type="number" className="form-control number-input" placeholder="0%" required />
                            </div>
                        </div>

                        <FormGroup>
                            <label>Tipo de Riesgo:</label>
                            <FormGroup className="radio-group-type" name="type">
                                <FormGroup className="Radio-element">

                                    <input
                                        id="risktype1"
                                        type="radio"
                                        value="externo"
                                        checked={value === "externo"}
                                        onChange={this.onChange}
                                    />
                                    <label for="risktype1">Externo</label>
                                </FormGroup>
                                <FormGroup className="Radio-element">

                                    <input
                                        id="risktype2"
                                        type="radio"
                                        value="interno"
                                        checked={value === "interno"}
                                        onChange={this.onChange}
                                    />
                                    <label for="risktype2">Interno</label>
                                </FormGroup>
                            </FormGroup>

                        </FormGroup>


                        <div className="form-group">
                            <label>Tipo: </label>
                            <Form.Select name="areatype" id="areatype" hidden={value === "interno"}>
                                <option value="" defaultValue disabled hidden>Seleccione una fuente por área</option>
                                <option value="political">Político</option>
                                <option value="legal">Legal</option>
                                <option value="economic">Económico</option>
                                <option value="it">Tecnologías de la información</option>
                                <option value="natural-events">Eventos naturales</option>
                                <option value="environmental">Ambiental</option>

                            </Form.Select>

                            <Form.Select name="areatype" id="areatype" hidden={value === "externo"}>
                                <option value="stretegic">Estratégicos</option>
                                <option value="financial">Financieros</option>
                                <option value="process_development">Desarrollo de los procesos</option>
                                <option value="tecnological_information">Tecnológicos y de información</option>
                                <option value="substantive_process_management">Gestión de procesos sustantivos</option>
                                <option value="municipal_official">Funcionario municipal</option>

                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Fuente por área específica:</label>
                            <input name="specific_factor" id="specific_factor" type="text" placeholder="" className="form-control" required />
                        </div>

                        <div className="form-group">
                            <label>Factores:</label>
                            <textarea name="factor" id="factor" type="text" placeholder="¿Por qué puede suceder?" className="form-control" />
                        </div>
                        <Button id="submitRiskBtn" className='btn-sfr' type="submit" >
                            Guardar
                        </Button>
                    </Form>
                </Modal.Body>
                <ToastContainer />
            </Modal>
        );
    }
};
export default AddRiskModal;
