import React, { Component } from 'react';
import axios from 'axios';

import { Modal, Button, Form, FormGroup } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../Riesgos.css'
class EditRiskModal extends Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.state = {
            value: "externo",
            area: "Politico"
        };
    }

    //closeModal() { }

    handleSubmit = (event) => {
        event.preventDefault();

        let options = {
            url: process.env.REACT_APP_API_URL + `/RiskManager/insert`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'name': event.target.name.value,
                'probability': parseFloat(event.target.probability.value),
                'areaType': this.state.area,
                'impact': parseInt(event.target.impact.value),
                'generalType': this.state.value,
                'factors': event.target.factor.value,
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
                    autoClose: 5000
                });
            });

    }

    onChange = e => {
        this.setState({ value: e.target.value })
    }

    handleAreaType = e =>{
        this.setState({area: e.target.value})
    }

    render() {

        const { value } = this.state;
        //let id = this.props.id
        //let magnitude = this.props.magnitude
        let render = this.props.show
        let closeModal = this.props.closeModalEdit
        //let risk = this.props.bringRiskToModal
        
        return (
            <Modal show={render} onHide={closeModal} id="modalRisks" >
                <Modal.Header closeButton>
                    Editar Riesgo
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
                                <input step=".1" min="0.1" max="0.9" name="probability" id="probability" type="number" placeholder="0,1" className="form-control number-input"  required />
                            </div>
                            <div className="number-input-container">
                                <label>Impacto:</label>
                                <input min="10" max="90" step="10" name="impact" id="impact" type="number" className="form-control number-input" placeholder="10%"  required />
                            </div>
                        </div>

                        <FormGroup>
                            <label>Tipo de Riesgo:</label>
                            <FormGroup className="radio-group-type" name="type" >
                                <FormGroup className="Radio-element">

                                    <input
                                        id="risktype1"
                                        type="radio"
                                        value="EXTERNO"
                                        checked={value === "externo"}
                                        onChange={this.onChange}
                                    />
                                    <label for="risktype1">Externo</label>
                                </FormGroup>
                                <FormGroup className="Radio-element">

                                    <input
                                        id="risktype2"
                                        type="radio"
                                        value="INTERNO"
                                        checked={value === "interno"}
                                        onChange={this.onChange}
                                    />
                                    <label for="risktype2">Interno</label>
                                </FormGroup>
                            </FormGroup>

                        </FormGroup>


                        <div className="form-group">
                            <label>Tipo: </label>
                            <Form.Select name="areatype" id="areatype" hidden={value === "interno"} onChange={this.handleAreaType} >
                                
                                <option value="Político">Político</option>
                                <option value="Legal">Legal</option>
                                <option value="Económico">Económico</option>
                                <option value="Tecnologías de la información">Tecnologías de la información</option>
                                <option value="Eventos naturales">Eventos naturales</option>
                                <option value="Ambiental">Ambiental</option>

                            </Form.Select>

                            <Form.Select name="areatype" id="areatype" hidden={value === "externo"} onChange={this.handleAreaType} >
                                <option value="Estratégicos">Estratégicos</option>
                                <option value="Financieros">Financieros</option>
                                <option value="Desarrollo de los procesos">Desarrollo de los procesos</option>
                                <option value="Tecnológicos y de información">Tecnológicos y de información</option>
                                <option value="Gestión de procesos sustantivos">Gestión de procesos sustantivos</option>
                                <option value="Funcionario municipal">Funcionario municipal</option>

                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Fuente por área específica:</label>
                            <input name="specific_factor" id="specific_factor" type="text" placeholder="" className="form-control"  required />
                        </div>
                        
                        {/*<div className="form-group">
                            <label>Factores:</label>
                            <textarea name="factor" id="factor" type="text" placeholder="¿Por qué puede suceder?" className="form-control" />
                        </div>*/}
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
export default EditRiskModal;