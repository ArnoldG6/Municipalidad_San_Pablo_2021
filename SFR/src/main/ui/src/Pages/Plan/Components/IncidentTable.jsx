import React, { Component } from 'react';
import { Table, Button, Accordion, OverlayTrigger, Tooltip } from "react-bootstrap";
import AddIncidentModal from './AddIncidentModal';
import GenericModal from '../../../SharedComponents/GenericModal/GenericModal';

class RiskTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            riesgos: [],
            showIncidentModal: false,
            showDel: false,
            delID: ""
        };
        this.removeIncident = this.removeIncident.bind(this);
        this.openModalAddIncident = this.openModalAddIncident.bind(this);
        this.closeModalAddIncident = this.closeModalAddIncident.bind(this);
        this.openModalDelIncident = this.openModalDelIncident.bind(this);
        this.closeModalDelIncident = this.closeModalDelIncident.bind(this);
    }

    removeIncident() {
        this.props.removeRisks(this.state.delID);
        this.closeModalDelRisk();
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
                    <Button size="sm" onClick={this.openModalAddIncident} variant="success" key="AddRiskButtonMobile">
                        <i className="bi bi-plus-square"></i> {' '}
                        Agregar Riesgo
                    </Button>
                    {(typeof this.props.riesgos === 'undefined' || this.props.riesgos === null) ? <h1>No se han agregado riesgos</h1> :
                        this.props.riesgos.length === 0 ? <h1>No se han agregado riesgos</h1> :
                            <Accordion className='mt-2'>
                                {this.props.riesgos.map((risk) => {
                                    return (
                                        <Accordion.Item eventKey={risk.id} key={risk.id}>
                                            <Accordion.Header >
                                                {risk.name}
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <p>
                                                    ID: {risk.id} <br />
                                                    Tipo General: {risk.generalType} <br />
                                                    Tipo por Área: {risk.areaType} <br />
                                                    Tipo Específico: {risk.specType} <br />
                                                    Probabilidad: {risk.probability} <br />
                                                    Impacto: {risk.impact} <br />
                                                    Magnitud: {risk.magnitude} <br />
                                                </p>
                                                <Button variant={sessionStorage.getItem("userRol") === "USER" ? "outline-dark" : "outline-danger"}
                                                    onClick={() => this.openModalDelIncident(risk.id)}
                                                    disabled={sessionStorage.getItem("userRol") === "USER" ? true : false}>
                                                    <i className="bi bi-dash-square-fill"></i>{' '}
                                                    Remover Riesgo
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
                    <Table hover>
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Fecha</th>
                                <th>Descripción</th>
                                <th>Riesgo Asociado</th>
                                <th>Causas</th>
                                <th>Afectación</th>
                                <th>
                                    {/* Agregar Incidencia */}
                                    <OverlayTrigger
                                        delay={{ hide: 450, show: 300 }}
                                        overlay={(props) => (
                                            <Tooltip {...props}>
                                                Agregar Incidencia
                                            </Tooltip>
                                        )}
                                        placement="left"
                                    >
                                        <Button size="lg" onClick={this.openModalAddIncident} variant="outline-success">
                                            <i className="bi bi-plus-square-fill"></i>
                                        </Button>
                                    </OverlayTrigger>

                                </th>
                            </tr>
                        </thead>
                        {(typeof this.props.riesgos === 'undefined' || this.props.riesgos === null) ? <h1>No se han agregado incidencias</h1> :
                            this.props.riesgos.length === 0 ? <h1>No se han agregado incidencias</h1> :
                                <tbody>
                                    {this.props.riesgos.map((risk) => {
                                        return (
                                            <tr key={risk.id}>
                                                <td>{risk.id}</td>
                                                <td>{risk.name}</td>
                                                <td>{risk.generalType}</td>
                                                <td>{risk.areaType}</td>
                                                <td>{risk.specType}</td>
                                                <td>
                                                    {/* Eliminar Riesgo */}
                                                    <OverlayTrigger
                                                        delay={{ hide: 450, show: 300 }}
                                                        overlay={(props) => (
                                                            <Tooltip {...props}>
                                                                Remover Incidencia
                                                            </Tooltip>
                                                        )}
                                                        placement="left"
                                                    >
                                                        <Button size="lg" variant={sessionStorage.getItem("userRol") === "USER" ? "outline-dark" : "outline-danger"}
                                                            onClick={() => this.openModalDelIncident(risk.id)}
                                                            disabled={sessionStorage.getItem("userRol") === "USER" ? true : false}>
                                                            <i className="bi bi-dash-square-fill"></i>
                                                        </Button>
                                                    </OverlayTrigger>
                                                </td>
                                            </tr>
                                        )
                                    })}
                                </tbody>
                        }
                    </Table>
                </div>
                <AddIncidentModal
                    risks={this.props.availableRisks}
                    addIncident={this.props.addIncident}
                    show={this.state.showIncidentModal}
                    closeModal={this.closeModalAddIncident} />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelIncident}
                    action={this.removeIncident}
                    header={"Eliminar Riesgo de un Plan"}
                    body={"¿Esta seguro que desea eliminar este riesgo del Plan?"} />
            </div>
        );
    }
};
export default RiskTable;