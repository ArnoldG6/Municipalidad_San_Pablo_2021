import React, { Component } from 'react';
import axios from 'axios';
import { Modal, Button, Form, FormGroup, Stack, OverlayTrigger, Tooltip } from "react-bootstrap";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../Riesgos.css'

class EditRiskModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            value: "Externo"
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.handleAreaType = this.handleAreaType.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit = (event) => {
        event.preventDefault();
        let options = {
            url: process.env.REACT_APP_API_URL + `/RiskManager/Edit`,
            method: 'PUT',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'id': event.target[0].value,
                'name': event.target[1].value,
                'probability': parseFloat(event.target[2].value),
                'impact': parseInt(event.target[3].value),
                'areaType': event.target[4].checked ? event.target[6].value : event.target[7].value,
                'generalType': event.target[4].checked ? event.target[4].value : event.target[5].value,
                'specType': event.target[8].value,
                'magnitude': parseFloat(event.target[2].value) * parseInt(event.target[3].value),
                'factors': event.target.factor.value
            }
        }
        axios(options)
            .then(response => {
                this.props.refreshPage();
                this.props.closeModalEdit();
            }).catch(error => {
                console.log(error);
            });
    }

    onChange = e => {
        this.props.risk.generalType = e.target.value;
        this.setState({ value: e.target.value })
    }

    handleAreaType = e => {
        this.props.risk.areaType = e.target.value;
        this.setState({ area: e.target.value })
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
                        <Form onSubmit={this.handleSubmit}>
                            <div className="form-group">
                                <label>ID: </label>
                                <input name="ID" id="ID" type="text" placeholder="ID" className="form-control" disabled defaultValue={risk.id} required />
                            </div>
                            <div className="form-group">
                                <label>Nombre: </label>
                                <input name="name" id="name" type="text" placeholder="Nombre" className="form-control" defaultValue={risk.name} required />
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
                                    <input step=".1" min="0.1" max="0.9" name="probability" id="probability" type="number" placeholder="0,1" className="form-control number-input" defaultValue={risk.probability} required />
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
                                    <input min="10" max="90" step="10" name="impact" id="impact" type="number" className="form-control number-input" placeholder="10%" defaultValue={risk.impact} required />
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
                                <Form.Select name="areatype" id="areatype" defaultValue={risk.areaType}>
                                    {
                                        (this.props.typesMap === null || typeof this.props.typesMap === 'undefined' || typeof this.props.typesMap.get(this.state.value) === 'undefined') ?
                                            <option value={null} disabled>Error cargando Subtipos</option> :
                                            this.props.typesMap.get(this.state.value).map((tipos) => {
                                                return <option value={tipos.name}>{tipos.name}</option>
                                            })
                                    }
                                </Form.Select>
                            </div>
                            <div className="form-group">
                                <label>Descripción de tipo específico:</label>
                                <input name="specific_factor" id="specific_factor" type="text" placeholder="" className="form-control" defaultValue={risk.specType} required />
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
                                <textarea name="factor" id="factor" type="text" placeholder="¿Por qué puede suceder?" defaultValue={risk.factors} className="form-control" />
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