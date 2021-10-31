import React, { Component } from 'react';
import axios from 'axios';

import { Modal, Button, Form, Row } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../Riesgos.css'
class AddRiskModal extends Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this)
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

    render() {
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

                      
                        <div className="form-group">
                            <label>Estado: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <Form.Select name="status" id="status">
                                <option value="" defaultValue disabled hidden>Seleccione un estado</option>
                                <option value="Activo">Activo</option>
                                <option value="Inactivo">Inactivo</option>
                                <option value="Completo">Completo</option>
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Autor: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <input name="authorName" id="authorName" type="text" placeholder="Autor" className="form-control" required />
                        </div>
                        <div className="form-group">
                            <label>Tipo: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <Form.Select name="type" id="type">
                                <option value="" defaultValue disabled hidden>Seleccione un tipo</option>
                                <option value="Proceso">Proceso</option>
                                <option value="Proyecto">Proyecto</option>
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Descripción: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
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
