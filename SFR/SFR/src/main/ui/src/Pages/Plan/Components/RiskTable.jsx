import React, { Component } from 'react';
import { Button, ListGroup } from "react-bootstrap";
import { Link } from 'react-router-dom';
import AddExistingRiskModal from './AddExistingRiskModal';
import GenericModal from '../../../SharedComponents/GenericModal/GenericModal';

class RiskTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            riesgos: [],
            showRiskModal: false,
            showDel: false,
            delID: ""
        };
        this.removeRisk = this.removeRisk.bind(this);
        this.openModalAddRisk = this.openModalAddRisk.bind(this);
        this.closeModalAddRisk = this.closeModalAddRisk.bind(this);
        this.openModalDelRisk = this.openModalDelRisk.bind(this);
        this.closeModalDelRisk = this.closeModalDelRisk.bind(this);
    }

    removeRisk() {
        this.props.removeRisks(this.state.delID);
        this.closeModalDelRisk();
    }

    openModalAddRisk() {
        this.setState({ showRiskModal: true });
    };

    closeModalAddRisk() {
        this.setState({ showRiskModal: false });
    };

    openModalDelRisk(id) {
        this.setState({ showDel: true, delID: id });
    };

    closeModalDelRisk() {
        this.setState({ showDel: false, delID: "" });
    };

    render() {
        return (
            <div>
                <div className='container-fluid'>
                    <Button
                        size="sm"
                        onClick={this.openModalAddRisk}
                        variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") || this.props.permsCheck("INVOLVED") ? "success" : "dark"}
                        disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") && !this.props.permsCheck("INVOLVED") ? true : false}
                        key="AddRiskButtonMobile">
                        <i className="bi bi-plus-square"></i> {' '}
                        Agregar Riesgo
                    </Button>
                    {(typeof this.props.riesgos === 'undefined' || this.props.riesgos === null) ? <h1>No se han agregado riesgos</h1> :
                        this.props.riesgos.length === 0 ? <h1>No se han agregado riesgos</h1> :
                            <ListGroup className='mt-2'>
                                {this.props.riesgos.map((risk) => {
                                    return (
                                        <ListGroup.Item className="d-flex justify-content-between align-items-start" key={risk.id}>
                                            <div className="ms-2 me-auto">
                                                <div className="fw-bold">{risk.name}</div>
                                                {"Tipo por área: " + risk.areaType} <br />
                                                <Button variant="link">
                                                    <Link to={{ pathname: "/riesgo", search: `?id=${risk.id}` }}>+ Más información</Link>
                                                </Button>
                                                <Button className="d-lg-none"
                                                    variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") ? "outline-danger" : "outline-dark"}
                                                    disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") ? true : false}
                                                    onClick={() => this.openModalDelRisk(risk.pkID)} >
                                                    <i className="bi bi-dash-square-fill"></i>{' '}
                                                    Remover Riesgo
                                                </Button>
                                            </div>
                                            <Button className="d-none d-lg-block"
                                                variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") ? "outline-danger" : "outline-dark"}
                                                disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") ? true : false}
                                                onClick={() => this.openModalDelRisk(risk.pkID)} >
                                                <i className="bi bi-dash-square-fill"></i>{' '}
                                                Remover Riesgo
                                            </Button>

                                        </ListGroup.Item>
                                    );
                                })}
                            </ListGroup>
                    }
                </div>

                <AddExistingRiskModal
                    risks={this.props.availableRisks}
                    addRisk={this.props.addRisk}
                    show={this.state.showRiskModal}
                    closeModal={this.closeModalAddRisk} />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelRisk}
                    action={this.removeRisk}
                    header={"Eliminar Riesgo de un Plan"}
                    body={"¿Esta seguro que desea eliminar este riesgo del Plan?"} />
            </div >
        );
    }
};
export default RiskTable;