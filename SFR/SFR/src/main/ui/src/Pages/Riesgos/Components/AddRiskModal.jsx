import React, { Component } from 'react';
import axios from 'axios';
import { Modal, Button, Form, FormGroup, Stack, OverlayTrigger, Tooltip } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../Riesgos.css'
import Cookies from 'universal-cookie';
const cookies = new Cookies();

class AddRiskModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            validated: false,
            generalType: "Externo",
            areaType: "Político",
            specificType: "Cambios de gobierno de la República",
            defaultConsequence: ""
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.handleAreaType = this.handleAreaType.bind(this);
        this.handleAreaSpecificType = this.handleAreaSpecificType.bind(this);
        this.setValidated = this.setValidated.bind(this);
        this.getID = this.getID.bind(this);
        this.getConsequence = this.getConsequence.bind(this);
    }

    handleSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            event.preventDefault();
            let id = this.getID(this.state.generalType, this.state.areaType, event.target.areaSpecifictype.value);
            let consequence = this.getConsequence(this.state.areaType, event.target.areaSpecifictype.value);
            let options = {
                url: process.env.REACT_APP_SFR_API_URL + `/RiskManager/Insert`,
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
                    'generalType': this.state.generalType,
                    'areaType': event.target.areatype.value,
                    'areaSpecificType': event.target.areaSpecifictype.value,
                    'description': event.target.description.value,
                    'factors': event.target.factor.value,
                    'mitigationMeasures': event.target.mitigationMeasures.value,
                    'consequences': consequence,
                    'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH })
                }
            }

            axios(options)
                .then(response => {
                    this.setState({
                        validated: false
                    })
                    this.props.updateRiesgos("add-success");
                    this.props.closeModal();
                    if (response.data.id)
                        document.location = process.env.REACT_APP_SFR + "#/riesgo?id=" + response.data.id
                    else
                        throw new Error('Error al cargar página específica del riesgo ');
                })
                .catch(error => {
                    var msj = "";
                    if (error.response) {
                        //Server responded with an error
                        switch (error.response.status) {
                            case 401:
                                msj = "Hubo un problema insertando el Riesgo.";
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

    getID(generalType, areaType, areaSpecificType) {
        let id = "";
        this.props.typesMap.get(generalType).map((tipo) => {
            if (tipo.name === areaType) {
                id += tipo.idName + "-";
            }
            return tipo.idName;
        })
        this.props.typesMap.get(areaType).map((tipo) => {
            if (tipo.name === areaSpecificType) {
                id += tipo.idName;
            }
            return tipo.idName;
        })

        return id;
    }

    getConsequence(areaType, areaSpecificType) {
        let consequence = "";
        this.props.typesMap.get(areaType).map((tipo) => {
            if (tipo.name === areaSpecificType) {
                consequence = tipo.consequence;
            }
            return tipo.consequence;
        })
        return consequence;
    }

    onChange = e => {
        this.setState({
            generalType: e.target.value,
            areaType: e.target.value === "Externo" ? "Político" : "Estratégicos"
        })
    }

    handleAreaType = e => {
        this.setState({ areaType: e.target.value })
    }

    handleAreaSpecificType = e => {
        this.setState({ specificType: e.target.value })
    }

    render() {
        let render = this.props.show;
        let closeModal = this.props.closeModal;

        return (
            <Modal show={render} onHide={() => { this.setState({ validated: false, generalType: "Externo", areaType: "Político" }); closeModal() }} id="modalRisks" >
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
                            </Stack>
                        </div>

                        <FormGroup>
                            <Stack direction="horizontal" gap={3}>
                                <label>Tipo General del Riesgo:</label>
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
                                                        checked={this.state.generalType === tipos.name}
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
                                <label>Tipo por Área del Riesgo: </label>
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

                            <Form.Select name="areatype" id="areatype" onChange={this.handleAreaType}>
                                {
                                    (this.props.typesMap === null || typeof this.props.typesMap === 'undefined' || typeof this.props.typesMap.get(this.state.generalType) === 'undefined') ?
                                        <option value={null} key="disabledSubtypeRisk" disabled>Error cargando Subtipos</option> :
                                        this.props.typesMap.get(this.state.generalType).map((tipos) => {
                                            return <option value={tipos.name} key={tipos.name}>{tipos.name}</option>
                                        })
                                }
                            </Form.Select>
                        </div>

                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <label>Tipo por Área Específica del Riesgo: </label>
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

                            <Form.Select name="areaSpecifictype" id="areaSpecifictype" onChange={this.handleAreaSpecificType}>
                                {
                                    (this.props.typesMap === null || typeof this.props.typesMap === 'undefined' || typeof this.props.typesMap.get(this.state.areaType) === 'undefined') ?
                                        <option value={null} key="disabledSubtypeRisk" disabled>Error cargando Subtipos</option> :
                                        this.props.typesMap.get(this.state.areaType).map((tipos) => {
                                            return <option value={tipos.name} key={tipos.name}>{tipos.name}</option>
                                        })
                                }
                            </Form.Select>
                        </div>
                        <Form.Group>
                            <div className="form-group">
                                <Form.Label>Descripción del riesgo:</Form.Label>
                                <textarea name="description" id="description" placeholder='Descripcion breve del riesgo.' type="text" className="form-control" required />
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

                            <textarea name="mitigationMeasures" id="mitigationMeasures" type="text" placeholder="Medidas necesarias para mitigar el riesgo." className="form-control" required />
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
