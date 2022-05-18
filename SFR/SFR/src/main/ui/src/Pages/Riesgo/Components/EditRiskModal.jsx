import React, { Component } from 'react';
import axios from 'axios';
import { Modal, Button, Form, FormGroup, Stack, OverlayTrigger, Tooltip } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Cookies from 'universal-cookie';
const cookies = new Cookies();

class EditRiskModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            validated: false,
            value: "Externo"
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.setValidated = this.setValidated.bind(this);
    }

    handleSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            event.preventDefault();
            let options = {
                url: process.env.REACT_APP_SFR_API_URL + `/RiskManager/Edit`,
                method: 'PUT',
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: {
                    'pkID': this.props.risk.pkID,
                    'id': this.props.risk.id,
                    'name': event.target.name.value,
                    'probability': parseFloat(event.target.probability.value),
                    'impact': parseInt(event.target.impact.value),
                    'generalType': this.props.risk.generalType,
                    'areaType': this.props.risk.areaType,
                    'areaSpecificType': this.props.risk.areaSpecificType,
                    'description': event.target.description.value,
                    'magnitude': parseFloat(event.target.probability.value) * parseInt(event.target.impact.value),
                    'factors': event.target.factor.value,
                    'mitigationMeasures': event.target.mitigationMeasures.value,
                    'author': this.props.risk.author,
                    'consequences': event.target.consequences.value,
                    'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH })
                }
            }

            axios(options)
                .then(response => {
                    this.props.refreshPage();
                    this.props.closeModalEdit();
                })
                .catch(error => {
                    var msj = "";
                    if (error.response) {
                        //Server responded with an error
                        switch (error.response.status) {
                            case 400:
                                msj = "Hubo un problema encontrando el Riesgo por actualizar.";
                                break;
                            case 401:
                                msj = "Este usuario no cuenta con permisos para editar este Riesgo.";
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
        this.setValidated(true);
    }

    setValidated(value) {
        this.setState({ validated: value });
    }

    render() {
        let render = this.props.show;
        let closeModal = this.props.closeModalEdit;
        let risk = this.props.risk;
        return (
            <Modal show={render} onHide={closeModal} id="modalRisks" >
                <Modal.Header closeButton>
                    Editar Riesgo
                </Modal.Header>
                <Modal.Body>
                    {(typeof risk === 'undefined' || risk === null) ? <h1>Error cargando el riesgo</h1> :
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
                                        defaultValue={risk.name}
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Por favor ingresar nombre.
                                    </Form.Control.Feedback>
                                </div>
                            </Form.Group>
                            <div className="form-group">
                                <Stack direction='horizontal'>
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
                                        <input step=".01" min="0.01" max="1" name="probability" id="probability" type="number" placeholder="0,01" className="form-control number-input" defaultValue={risk.probability} required />
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
                                        <input min="1" max="100" step="1" name="impact" id="impact" type="number" className="form-control number-input" placeholder="10%" defaultValue={risk.impact} required />
                                    </div>
                                </Stack>
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
                                <FormGroup className="radio-group-type" name="type" defaultValue={risk.generalType} >
                                    {
                                        (this.props.typesMap === null || typeof this.props.typesMap === 'undefined') ?
                                            <h4>Error cargando Tipos</h4> :
                                            this.props.typesMap.get("parents").map((tipos) => {
                                                return (
                                                    <FormGroup className="Radio-element">
                                                        <input
                                                            id={tipos.id}
                                                            type="radio"
                                                            value={tipos.name}
                                                            checked={tipos.name === risk.generalType}
                                                            disabled
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
                                                {process.env.REACT_APP_RIESGOS_HELP_TIPO_AREA}
                                            </Tooltip>
                                        )}
                                        placement="bottom"
                                    >
                                        <h5 className='ms-auto mt-1'>
                                            <i className="bi bi-info-circle"></i>
                                        </h5>
                                    </OverlayTrigger>
                                </Stack>
                                <Form.Select name="areatype" id="areatype" defaultValue={risk.areaType} disabled>
                                    {
                                        (this.props.typesMap === null || typeof this.props.typesMap === 'undefined' || typeof this.props.typesMap.get(risk.generalType) === 'undefined') ?
                                            <option value={null} disabled>Error cargando Subtipos</option> :
                                            this.props.typesMap.get(risk.generalType).map((tipos) => {
                                                return <option value={tipos.name}>{tipos.name}</option>
                                            })
                                    }
                                </Form.Select>
                            </div>
                            <div className="form-group">
                                <Stack direction="horizontal" gap={3}>
                                    <label>Tipo por Área Específica: </label>
                                    <OverlayTrigger
                                        delay={{ hide: 450, show: 300 }}
                                        overlay={(props) => (
                                            <Tooltip {...props}>
                                                {process.env.REACT_APP_RIESGOS_HELP_TIPO_AREA_ESPE}
                                            </Tooltip>
                                        )}
                                        placement="bottom"
                                    >
                                        <h5 className='ms-auto mt-1'>
                                            <i className="bi bi-info-circle"></i>
                                        </h5>
                                    </OverlayTrigger>
                                </Stack>
                                <Form.Select name="areaSpecifictype" id="areaSpecifictype" defaultValue={risk.areaSpecificType} disabled>
                                    {
                                        (this.props.typesMap === null || typeof this.props.typesMap === 'undefined' || typeof this.props.typesMap.get(risk.areaType) === 'undefined') ?
                                            <option value={null} disabled>Error cargando Subtipos</option> :
                                            this.props.typesMap.get(risk.areaType).map((tipos) => {
                                                return <option value={tipos.name}>{tipos.name}</option>
                                            })
                                    }
                                </Form.Select>
                            </div>

                            <Form.Group>
                                <div className="form-group">
                                    <Stack direction="horizontal" gap={3}>
                                        <label>Descripción del riesgo: </label>
                                        <OverlayTrigger
                                            delay={{ hide: 450, show: 300 }}
                                            overlay={(props) => (
                                                <Tooltip {...props}>
                                                    {process.env.REACT_APP_RIESGOS_HELP_DESC}
                                                </Tooltip>
                                            )}
                                            placement="bottom"
                                        >
                                            <h5 className='ms-auto mt-1'>
                                                <i className="bi bi-info-circle"></i>
                                            </h5>
                                        </OverlayTrigger>
                                    </Stack>
                                    <textarea name="description" id="description" type="text" defaultValue={risk.description} className="form-control" required />
                                    <Form.Control.Feedback type="invalid">
                                        Por favor ingresar tipo específico.
                                    </Form.Control.Feedback>
                                </div>
                            </Form.Group>
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
                                <textarea name="factor" id="factor" type="text" placeholder="¿Por qué puede suceder?" defaultValue={risk.factors} className="form-control" />
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
                                <textarea name="mitigationMeasures" id="mitigationMeasures" type="text" placeholder="Medidas necesarias para mitigar el riesgo." defaultValue={risk.mitigationMeasures} className="form-control" />
                            </div>
                            <div className="form-group">
                                <Stack direction="horizontal" gap={3}>
                                    <label>Consecuencias:</label>
                                    <OverlayTrigger
                                        delay={{ hide: 450, show: 300 }}
                                        overlay={(props) => (
                                            <Tooltip {...props}>
                                                {process.env.REACT_APP_RIESGOS_HELP_CONSECUENCIAS}
                                            </Tooltip>
                                        )}
                                        placement="bottom"
                                    >
                                        <h5 className='ms-auto mt-1'>
                                            <i className="bi bi-info-circle"></i>
                                        </h5>
                                    </OverlayTrigger>
                                </Stack>
                                <textarea name="consequences" id="consequences" type="text" placeholder="Consecuencias de este riesgo." defaultValue={risk.consequences} className="form-control" />
                            </div>
                            <Button id="submitRiskBtn" className='btn-sfr' type="submit" >
                                Guardar
                            </Button>
                        </Form>
                    }
                </Modal.Body>
                <ToastContainer />
            </Modal>
        );
    }
};
export default EditRiskModal;
