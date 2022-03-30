import React, { Component } from 'react';
import { Button, Accordion } from "react-bootstrap";
import AddIncidentModal from './AddIncidentModal';
import GenericModal from '../../../SharedComponents/GenericModal/GenericModal';

class IncidentTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            riesgos: [],
            incidentes: [],
            showIncidentModal: false,
            showDel: false,
            delID: "",
            planID: ""
        };
        this.removeIncidence = this.removeIncidence.bind(this);
        this.openModalAddIncident = this.openModalAddIncident.bind(this);
        this.closeModalAddIncident = this.closeModalAddIncident.bind(this);
        this.openModalDelIncident = this.openModalDelIncident.bind(this);
        this.closeModalDelIncident = this.closeModalDelIncident.bind(this);
    }

    removeIncidence() {
        this.props.removeIncidences(this.state.delID);
        this.closeModalDelIncident();
    }

    openModalAddIncident() {
        this.setState({ showIncidentModal: true });
    };

    closeModalAddIncident() {
        this.setState({ showIncidentModal: false });
    };

    openModalDelIncident(id) {
        this.setState({ showDel: true, delID: id });
    };

    closeModalDelIncident() {
        this.setState({ showDel: false, delID: "" });
    };

    render() {
        return (
            <div>
                {/* Mobile */}
                <div className='d-lg-none container-fluid'>
                    <Button size="sm" onClick={this.openModalAddIncident} variant="success" key="AddIncidenceButtonMobile">
                        <i className="bi bi-plus-square"></i> {' '}
                        Agregar Incidencia
                    </Button>
                    {(typeof this.props.incidentes === 'undefined' || this.props.incidentes === null) ? <h1>No se han agregado incidentes</h1> :
                        this.props.incidentes.length === 0 ? <h1>No se han agregado incidentes</h1> :
                            <Accordion className='mt-2'>
                                {this.props.incidentes.map((incidence) => {
                                    return (
                                        <Accordion.Item eventKey={incidence.pkID} key={incidence.pkID}>
                                            <Accordion.Header >
                                                {incidence.name}
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <p>
                                                    ID: {incidence.pkID} <br/>
                                                    Nombre: {incidence.name} <br/>
                                                    Fecha: {incidence.entryDate} <br/>
                                                    Causa: {incidence.cause} <br/>
                                                    Afectacion: {incidence.affectation} <br/>
                                                    Descripcion: {incidence.description} <br/>
                                                    Riesgos Asociados: {(typeof this.props.riesgos === "undefined" || this.props.riesgos === null) ?<div>No hay riesgos asociados</div> :
                                                    this.props.riesgos.length === 0 ? <div>No hay riesgos asociados</div> : this.props.riesgos.map((riesgo) => {return (<div>{riesgo.name}</div>);})}<br/>
                                                </p>
                                                <Button variant={sessionStorage.getItem("userRol") === "USER" ? "outline-dark" : "outline-danger"}
                                                    onClick={() => this.openModalDelIncident(incidence.pkID)}
                                                    disabled={sessionStorage.getItem("userRol") === "USER" ? true : false}>
                                                    <i className="bi bi-dash-square-fill"></i>{' '}
                                                    Remover Incidencia
                                                </Button>
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    );
                                })}
                            </Accordion>
                    }
                </div>
                {/* PC */}
                <div className="d-none d-lg-block">
                    <Button size="sm" onClick={this.openModalAddIncident} variant="success" key="AddIncidenceButtonMobile">
                        <i className="bi bi-plus-square"></i> {' '}
                        Agregar Incidencia
                    </Button>
                    {(typeof this.props.incidentes === 'undefined' || this.props.incidentes === null) ? <h1>No se han agregado incidentes</h1> :
                        this.props.incidentes.length === 0 ? <h1>No se han agregado incidentes</h1> :
                            <Accordion className='mt-2'>
                                {this.props.incidentes.map((incidence) => {
                                    return (
                                        <Accordion.Item eventKey={incidence.pkID} key={incidence.pkID}>
                                            <Accordion.Header >
                                                {incidence.name}
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <p>
                                                    ID: {incidence.pkID} <br />
                                                    Nombre: {incidence.name} <br />
                                                    Fecha: {incidence.entryDate} <br />
                                                    Causa: {incidence.cause} <br />
                                                    Afectacion: {incidence.affectation} <br />
                                                    Descripcion: {incidence.description} <br />
                                                    Riesgos Asociados: {(typeof this.props.riesgos === "undefined" || this.props.riesgos === null) ?<div>No hay riesgos asociados</div> :
                                                    this.props.riesgos.length === 0 ? <div>No hay riesgos asociados</div> : this.props.riesgos.map((riesgo) => {return (<div>{riesgo.name}</div>);})}<br/>
                                                </p>
                                                <Button variant={sessionStorage.getItem("userRol") === "USER" ? "outline-dark" : "outline-danger"}
                                                    onClick={() => this.openModalDelIncident(incidence.pkID)}
                                                    disabled={sessionStorage.getItem("userRol") === "USER" ? true : false}>
                                                    <i className="bi bi-dash-square-fill"></i>{' '}
                                                    Remover Incidencia
                                                </Button>
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    );
                                })}
                            </Accordion>
                    }
                </div>
                <AddIncidentModal
                    risks={this.props.riesgos}
                    planID={this.props.planID}
                    show={this.state.showIncidentModal}
                    closeModal={this.closeModalAddIncident} />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelIncident}
                    action={this.removeIncidence}
                    header={"Eliminar incidente de un Plan"}
                    body={"¿Esta seguro que desea eliminar este incidente del Plan?"} />
            </div>
        );
    }
};
export default IncidentTable;