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
            value:"externo"
        };
    }
    
    //closeModal() { }

    handleSubmit = (event) => {
        event.preventDefault();

        let options = {
            url: `http://localhost:8080/SFR/API/PlanManager/insert`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'name': event.target.name.value,
                'id': event.target.id.value,
                'status': event.target.status.value,
                'authorName': event.target.authorName.value,
                'type': event.target.type.value,
                'description': event.target.description.value
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

    onChange = e =>{
        this.setState({value: e.target.value})
    }
    

    render() {
        
        const{value} = this.state;


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
                            <div className="number-input-container">
                                <label>Probabilidad: </label>
                                <input name="probability" id="probability" type="text" placeholder="0.0" className="form-control number-input " required />
                            </div>
                            <div className="number-input-container">
                                <label>Impacto:</label>
                                <input name="impact" id="impact" type="text" className="form-control number-input" placeholder="0%" required />
                            </div>
                        </div>

                        <FormGroup>
                            <label>Tipo de Riesgo:</label>
                            <FormGroup  className="radio-group-type">
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
                            <Form.Select name="external-area-type" id="external-area-type" show={value === "externo"} onChange={this.onChange}>
                                <option value="" defaultValue disabled hidden>Seleccione una fuente por área</option>
                                <option value="political">Político</option>
                                <option value="legal">Legal</option>
                                <option value="economic">Económico</option>
                                <option value="it">Tecnologías de la información</option>
                                <option value="natural-events">Eventos naturales</option>
                                <option value="environmental">Ambiental</option>
                                
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Descripción:</label>
                            <textarea name="description" id="description" type="text" placeholder="Descripción" className="form-control" />
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
