import React, { Component } from 'react';
import axios from 'axios';

import { Modal, Button, Form, FormGroup, Stack, OverlayTrigger, Tooltip } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../Riesgos.css'
class AddRiskModal extends Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.state = {
            value: "EXTERNO",
            area: "Politico"
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.handleAreaType = this.handleAreaType.bind(this);
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
                'specType': event.target.specific_factor.value
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

    handleAreaType = e => {
        this.setState({ area: e.target.value })
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
                                <Stack direction="horizontal" gap={3}>
                                    <label>Probabilidad: </label>
                                    <OverlayTrigger
                                        delay={{ hide: 450, show: 300 }}
                                        overlay={(props) => (
                                            <Tooltip {...props}>
                                                {process.env.REACT_APP_RIESGOS_HELP_PROB}
                                            </Tooltip>
                                        )}
                                        placement="bottom"
                                    >
                                        <h5 className='ms-auto mt-1'>
                                            <i className="bi bi-info-circle"></i>
                                        </h5>
                                    </OverlayTrigger>
                                </Stack>
                                <input step=".1" min="0.1" max="0.9" name="probability" id="probability" type="number" placeholder="0,1" className="form-control number-input " required />
                            </div>
                            <div className="number-input-container">
                                <Stack direction="horizontal" gap={3}>
                                    <label>Impacto:</label>
                                    <OverlayTrigger
                                        delay={{ hide: 450, show: 300 }}
                                        overlay={(props) => (
                                            <Tooltip {...props}>
                                                {process.env.REACT_APP_RIESGOS_HELP_IMPACTO}
                                            </Tooltip>
                                        )}
                                        placement="bottom"
                                    >
                                        <h5 className='ms-auto mt-1'>
                                            <i className="bi bi-info-circle"></i>
                                        </h5>
                                    </OverlayTrigger>
                                </Stack>
                                <input min="10" max="90" step="10" name="impact" id="impact" type="number" className="form-control number-input" placeholder="10%" required />
                            </div>
                        </div>

                        <FormGroup>
                            <Stack direction="horizontal" gap={3}>
                                <label>Tipo de Riesgo:</label>
                                <OverlayTrigger
                                    delay={{ hide: 450, show: 300 }}
                                    overlay={(props) => (
                                        <Tooltip {...props}>
                                            {process.env.REACT_APP_RIESGOS_HELP_TIPO_RIESGO}
                                        </Tooltip>
                                    )}
                                    placement="bottom"
                                >
                                    <h5 className='ms-auto mt-1'>
                                        <i className="bi bi-info-circle"></i>
                                    </h5>
                                </OverlayTrigger>
                            </Stack>

                            <FormGroup className="radio-group-type" name="type">
                                <FormGroup className="Radio-element">

                                    <input
                                        id="risktype1"
                                        type="radio"
                                        value="EXTERNO"
                                        checked={value === "EXTERNO"}
                                        onChange={this.onChange}
                                    />
                                    <label for="risktype1">Externo</label>
                                </FormGroup>
                                <FormGroup className="Radio-element">

                                    <input
                                        id="risktype2"
                                        type="radio"
                                        value="INTERNO"
                                        checked={value === "INTERNO"}
                                        onChange={this.onChange}
                                    />
                                    <label for="risktype2">Interno</label>
                                </FormGroup>
                            </FormGroup>

                        </FormGroup>


                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <label>Tipo: </label>
                                <OverlayTrigger
                                    delay={{ hide: 450, show: 300 }}
                                    overlay={(props) => (
                                        <Tooltip {...props}>
                                            {process.env.REACT_APP_RIESGOS_HELP_TIPO}
                                        </Tooltip>
                                    )}
                                    placement="bottom"
                                >
                                    <h5 className='ms-auto mt-1'>
                                        <i className="bi bi-info-circle"></i>
                                    </h5>
                                </OverlayTrigger>
                            </Stack>

                            <Form.Select name="areatype" id="areatype" hidden={value === "INTERNO"} onChange={this.handleAreaType}>
                                <option value="" defaultValue disabled hidden>Seleccione una fuente por área</option>
                                <option value="Político">Político</option>
                                <option value="Legal">Legal</option>
                                <option value="Económico">Económico</option>
                                <option value="Tecnologías de la información">Tecnologías de la información</option>
                                <option value="Eventos naturales">Eventos naturales</option>
                                <option value="Ambiental">Ambiental</option>

                            </Form.Select>

                            <Form.Select name="areatype" id="areatype" hidden={value === "EXTERNO"} onChange={this.handleAreaType}>
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
                            <input name="specific_factor" id="specific_factor" type="text" placeholder="" className="form-control" required />
                        </div>

                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <label>Factores:</label>
                                <OverlayTrigger
                                    delay={{ hide: 450, show: 300 }}
                                    overlay={(props) => (
                                        <Tooltip {...props}>
                                            {process.env.REACT_APP_RIESGOS_HELP_FACTORES}
                                        </Tooltip>
                                    )}
                                    placement="bottom"
                                >
                                    <h5 className='ms-auto mt-1'>
                                        <i className="bi bi-info-circle"></i>
                                    </h5>
                                </OverlayTrigger>
                            </Stack>
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
