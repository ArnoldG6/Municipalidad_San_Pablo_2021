import React, { Component } from 'react';
import axios from 'axios';
import { Modal, Button, Form, FormGroup, Stack, OverlayTrigger, Tooltip } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../Riesgos.css'

class AddRiskModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            validated: false,
            value: "Externo"
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.handleAreaType = this.handleAreaType.bind(this);
        this.setValidated = this.setValidated.bind(this);
        this.getID = this.getID.bind(this);
    }

    handleSubmit = (event) => {
        const form = event.currentTarget;
        if(form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else{
        event.preventDefault();
        let id = this.getID(this.state.value, event.target.areatype.value);
        let options = {
            url: process.env.REACT_APP_API_URL + `/RiskManager/Insert`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'id': id,
                'name': event.target.name.value,
                'probability': parseFloat(event.target.probability.value),
                'impact': parseInt(event.target.impact.value),
                'generalType': this.state.value,
                'areaType': event.target.areatype.value,
                'specType': event.target.specific_factor.value,
                'factors': event.target.factor.value,
                'mitigationMeasures': event.target.mitigationMeasures.value
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
        this.setValidated(true);
    }

    setValidated(value) {
        this.setState({ validated: value});
    }

    getID(type, subtype) {
        let id = "";
        this.props.typesMap.get(type).map((tipo) => {
            if (tipo.name === subtype) {
                id = tipo.idName;
            }
            return tipo.idName;
        })
        return id;
    }

    onChange = e => {
        this.setState({ value: e.target.value })
    }

    handleAreaType = e => {
        this.setState({ area: e.target.value })
    }

    render() {
        let render = this.props.show;
        let closeModal = this.props.closeModal;

        return (
            <Modal show={render} onHide={() => { this.setState({ value: "Externo" }); closeModal() }} id="modalRisks" >
                <Modal.Header closeButton>
                    Ingrese los datos para el nuevo Riesgo
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>
                        <Form.Group>
                        <div className="form-group">
                            <Form.Label>Nombre: </Form.Label>
                            <Form.Control
                                name="name"
                                id="name"
                                type="text"
                                placeholder="Nombre"
                                className="form-control"
                                required
                            />
                            <Form.Control.Feedback type="invalid">
                                Por favor ingresar nombre.
                            </Form.Control.Feedback>
                        </div>
                        </Form.Group>
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
                                <input step=".01" min="0.01" max="1" name="probability" id="probability" type="number" placeholder="0,01" className="form-control number-input" required />
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
                                <input min="1" max="100" step="1" name="impact" id="impact" type="number" className="form-control number-input" placeholder="1%" required />
                            </div>
                        </div>

                        <FormGroup>
                            <Stack direction="horizontal" gap={3}>
                                <label>Tipo General:</label>
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
                                {
                                    (this.props.typesMap === null || typeof this.props.typesMap === 'undefined') ?
                                        <h4>Error cargando Tipos</h4> :
                                        this.props.typesMap.get("parents").map((tipos) => {
                                            return (
                                                <FormGroup className="Radio-element">
                                                    <input
                                                        key={tipos.id}
                                                        id={tipos.id}
                                                        type="radio"
                                                        value={tipos.name}
                                                        checked={this.state.value === tipos.name}
                                                        onChange={this.onChange}
                                                    />
                                                    <label htmlFor={tipos.id}>{tipos.name}</label>
                                                </FormGroup>
                                            )
                                        })
                                }
                            </FormGroup>

                        </FormGroup>


                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <label>Tipo por Área: </label>
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

                            <Form.Select name="areatype" id="areatype" onChange={this.handleAreaType}>
                                {
                                    (this.props.typesMap === null || typeof this.props.typesMap === 'undefined' || typeof this.props.typesMap.get(this.state.value) === 'undefined') ?
                                        <option value={null} key="disabledSubtypeRisk" disabled>Error cargando Subtipos</option> :
                                        this.props.typesMap.get(this.state.value).map((tipos) => {
                                            return <option value={tipos.name} key={tipos.name}>{tipos.name}</option>
                                        })
                                }
                            </Form.Select>
                        </div>
                        <Form.Group>
                        <div className="form-group">
                            <Form.Label>Descripción de tipo específico:</Form.Label>
                            <Form.Control
                            name="specific_factor"
                            id="specific_factor" type="text"
                            placeholder=""
                            className="form-control"
                            required
                            />
                            <Form.Control.Feedback type="invalid">
                                Por favor ingresar tipo específico.
                            </Form.Control.Feedback>
                        </div>
                        </Form.Group>
                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <Form.Label>Factores:</Form.Label>
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
                            <textarea name="factor" id="factor" type="text" placeholder="¿Por qué puede suceder?" className="form-control" required />
                        </div>
                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <label>Medidas de mitigación:</label>
                                <OverlayTrigger
                                    delay={{ hide: 450, show: 300 }}
                                    overlay={(props) => (
                                        <Tooltip {...props}>
                                            {process.env.REACT_APP_RIESGOS_HELP_MEDI_MITI}
                                        </Tooltip>
                                    )}
                                    placement="bottom"
                                >
                                    <h5 className='ms-auto mt-1'>
                                        <i className="bi bi-info-circle"></i>
                                    </h5>
                                </OverlayTrigger>
                            </Stack>
                            <textarea name="mitigationMeasures" id="mitigationMeasures" type="text" placeholder="Medidas necesarias para mitigar el riesgo."  className="form-control" required/>
                        </div>
                        <div className='text-center'>
                            <Button className='btn-sfr' type="submit" >
                                Guardar
                            </Button>
                        </div>
                    </Form>
                </Modal.Body>
                <ToastContainer />
            </Modal>
        );
    }
};
export default AddRiskModal;
