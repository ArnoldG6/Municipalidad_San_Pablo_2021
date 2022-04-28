import React, { Component } from 'react';
import { Button, ListGroup } from "react-bootstrap";
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
                <div className="container-fluid">
                    <Button
                        size="sm"
                        onClick={this.openModalAddIncident}
                        variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") || this.props.permsCheck("INVOLVED") ? "success" : "dark"}
                        disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") && !this.props.permsCheck("INVOLVED") ? true : false}
                        key="AddIncidenceButtonMobile">
                        <i className="bi bi-plus-square"></i> {' '}
                        Agregar Incidencia
                    </Button>
                    {(typeof this.props.incidentes === 'undefined' || this.props.incidentes === null) ? <h1>No se han agregado incidentes</h1> :
                        this.props.incidentes.length === 0 ? <h1>No se han agregado incidentes</h1> :
                            <ListGroup className='mt-2'>
                                {this.props.incidentes.map((incidence) => {
                                    return (
                                        <ListGroup.Item className="d-flex justify-content-between align-items-start" key={incidence.pkID}>
                                            <div className="ms-2 me-auto">
                                                <div className="fw-bold">{incidence.name}</div>
                                                {"Ingresado: " + incidence.entryDate} <br />
                                                {"Causa: " + incidence.cause} <br />
                                                {"Afectación: " + incidence.affectation + "%"} <br />
                                                {"Descripcion: " + incidence.description} <br />
                                                {(typeof incidence.risk !== 'undefined' && incidence.risk !== null) ? "Riesgo asociado: " + incidence.risk.id + ' - ' + incidence.risk.name : null} <br />
                                                <Button className="d-lg-none"
                                                    variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") || this.props.permsCheck("INVOLVED") ? "outline-danger" : "outline-dark"}
                                                    disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") && !this.props.permsCheck("INVOLVED") ? true : false}
                                                    onClick={() => this.openModalDelIncident(incidence.pkID)}>
                                                    <i className="bi bi-dash-square-fill"></i>{' '}
                                                    Remover Incidencia
                                                </Button>
                                            </div>
                                            <Button className="d-none d-lg-block"
                                                variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") || this.props.permsCheck("INVOLVED") ? "outline-danger" : "outline-dark"}
                                                disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") && !this.props.permsCheck("INVOLVED") ? true : false}
                                                onClick={() => this.openModalDelIncident(incidence.pkID)}>
                                                <i className="bi bi-dash-square-fill"></i>{' '}
                                                Remover Incidencia
                                            </Button>
                                        </ListGroup.Item>
                                    );
                                })}
                            </ListGroup>
                    }
                </div>
                <AddIncidentModal
                    risks={this.props.riesgos}
                    planID={this.props.planID}
                    show={this.state.showIncidentModal}
                    refreshPage={this.props.refreshPage}
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